<%@page pageEncoding="UTF-8"%>
<div class="frame-side">
	<div class="frame-side-inner">
		<div class="menu-nav">
			<h3>权限管理</h3>
			<ul class="sub">
				<li>
					<h4>用户管理</h4>
					<ul class="sub">
						<li><a href="/user">用户创建</a></li>
						<li><a href="/user/update">用户密码修改</a></li>
						<li><a href="/user/reset">用户密码重置</a></li>
						<li><a href="/user/detail">用户资料</a></li>
					</ul>
				</li>

				<li>
					<h4>资源管理</h4>
					<ul class="sub">
						<li><a href="/resource/add">资源创建</a></li>
						<li><a href="/resource">资源查询</a></li>
					</ul>
				</li>
				<li>
					<h4>权限设置</h4>
					<ul class="sub">
						<li><a href="/authority/authority">权限设置</a></li>
					</ul>
				</li>
			</ul>
		</div>
		<div class="menu-nav">
			<h3>操作日志</h3>
		</div>
		<div class="menu-nav">
			<h3>应用管理</h3>
			<ul class="sub">
				<li><a href="/heartbeat/app">应用配置信息管理</a></li>
				<li><a href="/cache/deleteCache">缓存删除</a></li>
				<li><a href="/switchconfig/list">锁定开关管理</a></li>
				<li><a href="/switchconfig/save">新增开关</a></li>
			</ul>
		</div>
		<div class="menu-nav">
			<h3>默认区域码表管理</h3>
			<ul class="sub">
				<li>
					<h4>码表管理</h4>
					<ul class="sub">
						<li><a href="/code">code表维护</a></li>
						<li><a href="/area">查询区域配送关系</a></li>
					</ul>
				</li>
				<li>
					<h4>默认区域管理</h4>
					<ul class="sub">
						<li><a href="/default/town">查看默认乡(镇)</a></li>
						<li><a href="/default/districtcount">没有匹配默认区域数</a></li>
						<li><a href="/default/update">修改默认区域</a></li>
					</ul>
				</li>
			</ul>
		</div>
		<div class="menu-nav">
			<h3>开放平台</h3>
			<ul class="sub">
				<li><a href="/app/list">应用列表</a></li>
				<li><a href="/app/list/audit">已停用应用</a></li>
				<li><a href="/app/list/unaudit">已启用应用</a></li>
			</ul>
		</div>
		<div class="menu-nav">
			<h3>版本历史</h3>
			<ul class="sub">
				<li><a href="/roadmap/goeditor">发布版本</a></li>
				<li><a href="/roadmap/list">版本历史</a></li>
			</ul>
		</div>
	</div>
</div>