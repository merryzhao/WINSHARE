/*
 * @(#)AttachServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.employee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.dao.AttachDao;
import com.winxuan.ec.exception.AttachException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.employee.EmployeeAttach;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.support.util.FileType;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;
import com.winxuan.framework.util.RandomCodeUtils;

/**
 * description
 * 
 * @author HideHai
 * @version 1.0,2011-9-8
 */
@Service("attachService")
@Transactional(rollbackFor = Exception.class)
public class AttachServiceImpl implements AttachService, Serializable {

	private static final long serialVersionUID = -35410637571931534L;
	private static final Log LOG = LogFactory.getLog(ControllerConstant.class);

	private static final int SHEETROW = 50000;

	@InjectDao
	private AttachDao attachDao; 

	@Value("${path.console}")
	private String attahPath;
	
	@Autowired
    private CodeService codeService;

	public EmployeeAttach get(Long id) {
		return attachDao.get(id);
	}

	public void create(EmployeeAttach employeeAttach) {
		employeeAttach.setCreateTime(new Date());
		employeeAttach.setDown(false);
		attachDao.save(employeeAttach);
	}
	
	/**
     * 生成消息
     * @param employee
     * @param name
     * @param head
     * @param data
     */
    public void makeAttach(Employee employee, String name, List<String> head, List<List<String>> data){
        EmployeeAttach employeeAttach = new EmployeeAttach();
        employeeAttach.setEmployee(employee);
        employeeAttach.setName(name);
        employeeAttach.setType(FileType.XLS.toString());
        employeeAttach.setSort(codeService.get(Code.PRESENT_STATUS));
        try{
            addAttach(employeeAttach, head, data);
        } catch(AttachException e){
            LOG.warn(name + "出错", e);
            employeeAttach.setName(name + "出错了!" + e.getMessage());
            create(employeeAttach);
        }
    }

	public String addAttach(EmployeeAttach employeeAttach, List<String> head,
			List<List<String>> data) throws AttachException {
		try {
			String path = "";
			String fileName = "";
			String filePath = null;
			String returnPath = null;
			Long userId = employeeAttach.getEmployee().getId();
			path = attahPath + userId+"/";
			File directory = new File(path);
			if(!directory.exists()){
				directory.mkdir();
			}
			if (employeeAttach.getType().equals(FileType.XLS.toString())) {
				fileName = RandomCodeUtils.create(RandomCodeUtils.MODE_LETTER_LOWERCASE, MagicNumber.TEN) + ".xls";
				filePath = path + fileName;
//				wirteExcel(head, data,filePath);
				wirtePoiExcel(head, data, filePath);
			}
			returnPath = userId+"/"+fileName;
			employeeAttach.setPath(returnPath);
			create(employeeAttach);
			return returnPath;
		} catch (Exception e) {
			throw new AttachException(e);
		}
	}

	/**
	 * 检查头部和列数是否相等 数量过多，使用多sheet写入 如使用jxl，多调用.wirte()方法释放内存
	 * 设置employeeAttach的path路径
	 * 
	 * @param head
	 *            excel顶部行
	 * @param data
	 *            excel内容
	 */
	private void wirteExcel(List<String> head, List<List<String>> data,String filePath)
	throws RowsExceededException, WriteException, IOException {
		File file =  new File(filePath);
		file.createNewFile();
		OutputStream os = new FileOutputStream(file);
		LOG.info(filePath);
		WritableWorkbook wb = Workbook.createWorkbook(os);
		LOG.info("create file success!");
		int index = 0;
		// 创建第一张sheet
		WritableSheet ws = wb.createSheet("sheet" + index, index);
		// 设置标题
		for (int k = 0; k < head.size(); k++) {
			Label label = new Label(k, 0, head.get(k));
			ws.addCell(label);
		}
		for (int i = 0; i < data.size(); i++) {
			if (i % SHEETROW == 0 && i != 0) {
				index++;
				ws = wb.createSheet("sheet" + index, index);
				for (int k = 0; k < head.size(); k++) {
					Label label = new Label(k, 0, head.get(k));
					ws.addCell(label);
				}
			}
			List<String> datarow = data.get(i);
			for (int r = 0; r < datarow.size(); r++) {
				Label label = new Label(r, 1 + i % SHEETROW, datarow.get(r));
				ws.addCell(label);
			}
		}
		wb.write();
		LOG.info("write file success!");
		wb.close();
	}
	
	private void wirtePoiExcel(List<String> head, List<List<String>> data,String filePath) throws IOException{
	    File file =  new File(filePath);
	    OutputStream os = new FileOutputStream(file);
	    
	    HSSFWorkbook write = new HSSFWorkbook();
	    int index = 0;
	    int row = 1;
	    HSSFSheet sw = write.createSheet("sheet_" + index++);
	    HSSFRow wrow = null;
        writeHead(sw, head);
	    
        for(int j = 0; j < data.size(); j ++){
            if((j % SHEETROW == 0 && j != 0)){
                sw = write.createSheet("sheet_" + index++);
                writeHead(sw, head);
                row = 1;
            }
            wrow = sw.createRow(row ++);
            List<String> cols = data.get(j);
            for(int i = 0; i < cols.size(); i++){
                HSSFCell col = wrow.createCell(i);
                col.setCellValue(cols.get(i));
            }
        }
        LOG.info("需要写入EXCEL的数据准备完成!");
	    write.write(os);
	    LOG.info("写入EXCEL成功!");
	    os.close();
	}
	
	private void writeHead(HSSFSheet sw, List<String> head){
	    HSSFRow wrow = sw.createRow(0);
	    for (int k = 0; k < head.size(); k++) {
            HSSFCell col = wrow.createCell(k);
            col.setCellValue(head.get(k));
        }
	}

	private String writeCSV(List<String> head, List<List<String>> data) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.winxuan.ec.service.employee.AttachService#find(java.util.Map, java.lang.Short, com.winxuan.framework.pagination.Pagination)
	 */
	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<EmployeeAttach> find(Map<String, Object> parameters, Short sort,
			Pagination pagination) {
		return attachDao.find(parameters, sort, pagination);
	}

}
