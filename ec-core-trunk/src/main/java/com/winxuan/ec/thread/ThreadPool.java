package com.winxuan.ec.thread;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * 
 * @author zhoujun
 * @version 1.0,2011-9-7
 */

public abstract class ThreadPool implements Runnable {

	/**
	 * 日志
	 */
	protected static final Log LOG = LogFactory.getLog(ThreadPool.class);
	/**
	 * 排队点号、发送线程，
	 */
	private Thread thread;

	/**
	 * 排队队列
	 */
	private Queue queue = new LinkedBlockingQueue();

	/**
	 * 锁，仅此而已
	 * 
	 * @see #run()
	 * @see #add(Object)
	 */
	private Object mutex = new Object();

	/**
	 * 构造本类对象，同时启动侦听邮件的到达。
	 * 如果要阻止侦听和发送，应该调用close方法，在Spring的Context中"最好"配置destroy-method
	 * ="close"，不过这不是必须的。
	 */
	public ThreadPool() {
		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * 循环，它被作为thread runnable的run实现。
	 * 
	 * @see #add(Object)
	 */
	public void run() {
		while (!isClose()) {
			while (!isEmpty()) {
				Object object = poll();
				boolean executed = false;
				Throwable throwable = null;
				try {
					// 执行
					this.execute(object);
					executed = true;
				} catch (Exception ex) {
					throwable = ex;
					LOG.error(ex.getMessage(), ex);
				}

				try {
					announce(object, executed, throwable);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}

			}
			// 等~直到add方法的通知!
			synchronized (mutex) {
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 关闭ThreadPool!
	 */
	public void close() {
		queue.clear();
		queue = null;
	}

	/**
	 * 已经关闭？
	 */
	public boolean isClose() {
		return queue == null;
	}

	protected boolean isEmpty() {
		return queue != null && queue.isEmpty();
	}

	/**
	 * 加入排队机
	 */
	protected void add(Object obj) {
		Assert.notNull(queue);
		queue.add(obj);
		synchronized (mutex) {
			mutex.notify();
		}
	}

	protected Object poll() {
		Assert.notNull(queue);
		return queue.poll();
	}

	protected Object peek() {
		Assert.notNull(queue);
		return queue.peek();
	}

	/**
	 * 队列中对象执行完毕后的通知方法。 子类可重写此方法，如将通知写入一个文本文件或者数据库中。
	 */
	public void announce(Object object, boolean executed, Throwable throwable) {
		StringBuffer notice=new StringBuffer(object.toString());
		notice.append(" executed ");
		notice.append(executed ? "success " : "faild ");
		if(!executed&&throwable!=null){
			notice.append(throwable);
		}
		LOG.info(notice);
	}

	/**
	 * 查看队列中的对象是否全部运行完毕。
	 */
	public boolean isFinished() {
		return queue.isEmpty();
	}

	/**
	 * 查看队列中存在的对象。
	 */
	public Object[] findExisting() {
		return queue.toArray();
	}

	/**
	 * 需要重写此方法。 run方法会调用此方法
	 */
	public abstract void execute(Object object);

}
