package com.winxuan.ec.admin.controller.promotion;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.promotion.PromotionData;
import com.winxuan.ec.model.promotion.PromotionDataTree;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.promotion.PromotionDataService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 ******************************* 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-20 上午9:47:40 --!
 * @description:
 ******************************** 
 */
@Controller
@RequestMapping("/promotiondata")
public class PromotionDataContoller {

    private static final String ERROR_MSG_TEMP = "其中第:{index}行数据异常";

    private static final String COMPLE_MSG_TEMP = "总共解析{total}条数据,成功{success}条,失败{faild}条,请导入规范数据后重新导入";

    private Logger log = LoggerFactory.getLogger(getClass());

    private List<XSSFRow> errorXSSFRowList = new ArrayList<XSSFRow>();

    @Autowired
    private PromotionDataService promotionDataService;
    
    @Autowired
    private PromotionDataConvert promotionDataConvert;

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("/promotiondata/index");
    }

    @RequestMapping("/clearall")
    public ModelAndView clearAll() {
        ModelAndView mav = new ModelAndView("/promotiondata/index");
        promotionDataService.clearAll();
        mav.addObject(ControllerConstant.MESSAGE_KEY, "完成！");
        return mav;
    }

    @RequestMapping("/data")
    public ModelAndView groupParent(PromotionDataForm promotionDataForm, Pagination pagination) throws Exception {
        ModelAndView mav = new ModelAndView("/promotiondata/groupparent");
        PromotionDataTree pdt = promotionDataService.getRoot();
        Set<PromotionDataTree> restul = pdt.getChildren();
        for (PromotionDataTree promotionDataTree : restul) {
            JSONObject json = promotionDataConvert.convert(promotionDataTree);
            String result = json.toString().replaceAll(",", ",\n");
            mav.addObject("result", result);
        }

        return mav;
    }

    @RequestMapping(value = "/excelupload", method = RequestMethod.POST)
    public ModelAndView excelupload(MultipartHttpServletRequest multipartRequest, @MyInject Employee employee) {
        MultipartFile multipartFile = multipartRequest.getFile("file");
        ModelAndView mav = new ModelAndView("/promotiondata/index");
        String errorMsg = "";
        List<PromotionData> result = null;
        try {
            PromotionDataTree promotionDataTree = this.parsingTree(multipartFile, employee);
            promotionDataService.save(promotionDataTree);
            result = this.parsingExcel(multipartFile, employee);
            if (CollectionUtils.isNotEmpty(result)) {
                for (PromotionData promotionData : result) {
                    promotionDataService.save(promotionData);
                }
                errorMsg = this.errorMsg(result);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            errorMsg += e.getMessage();
        }

        mav.addObject(ControllerConstant.MESSAGE_KEY, errorMsg);
        errorXSSFRowList.clear();
        return mav;
    }

    private String errorMsg(List<PromotionData> result) {
        String finalMsg = "";
        int total = result.size() + errorXSSFRowList.size();
        finalMsg = COMPLE_MSG_TEMP.replace("{total}", total + "");
        finalMsg = finalMsg.replace("{success}", result.size() + "");
        finalMsg = finalMsg.replace("{faild}", errorXSSFRowList.size() + "");
        String errorIndex = "";
        for (XSSFRow xfr : errorXSSFRowList) {
            errorIndex += (xfr.getRowNum() + 1) + ",";
        }
        errorIndex = ERROR_MSG_TEMP.replace("{index}", errorIndex);
        finalMsg += errorIndex;
        return finalMsg;
    }

    private XSSFWorkbook readerFile(MultipartFile multipartFile) {
        InputStream is;
        try {
            is = multipartFile.getInputStream();
            XSSFWorkbook hssfWorkbook = new XSSFWorkbook(is);
            return hssfWorkbook;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;

    }

    public PromotionDataTree parsingTree(MultipartFile multipartFile, Employee employee) {
        PromotionDataTree root = promotionDataService.getRoot();
        List<PromotionDataTree> parentList = new ArrayList<PromotionDataTree>();
        XSSFWorkbook hssfWorkbook = readerFile(multipartFile);
        XSSFSheet xSSFSheet = hssfWorkbook.getSheetAt(0);
        int rows = xSSFSheet.getLastRowNum();
        for (int i = 1; i <= rows; i++) {
            XSSFRow xssfRow = xSSFSheet.getRow(i);
            XSSFCell cellVg = xssfRow.getCell(0);
            String name = cellVg.getStringCellValue();
            String childName = xssfRow.getCell(1).getStringCellValue();
            PromotionDataTree parent = findNode(parentList, name);

            if (parent == null) {
                parent = new PromotionDataTree();
                parent.setName(name);
                parentList.add(parent);
                root.addChildren(parent);
                parent.setParent(root);
                parent.setIndex(i);
            }
           
            PromotionDataTree children = findNode(parentList, childName);
            if (children == null) {
                children = new PromotionDataTree();
                children.setName(childName);
                children.setParent(parent);
                parent.addChildren(children);
                children.setIndex(i);
            }

        }
        root.setChildren(new HashSet<PromotionDataTree>(parentList));
        promotionDataService.save(root);
        return root;
    }

    private PromotionDataTree findNode(List<PromotionDataTree> parentList, String nodeName) {
        for (PromotionDataTree promotionDataTree : parentList) {
            return promotionDataTree.findNode(promotionDataTree, nodeName);
        }
        return null;
    }

    /**
     * 有框架可以解决下面的代码?就是把excel读出来
     * 
     * @param hssfWorkbook
     * @param employee
     * @return
     */
    private List<PromotionData> parsingExcel(MultipartFile multipartFile, Employee employee) {
        List<PromotionData> result = null;
        XSSFWorkbook hssfWorkbook = readerFile(multipartFile);
        XSSFSheet xSSFSheet = hssfWorkbook.getSheetAt(0);
        int rows = xSSFSheet.getLastRowNum();
        if (rows > 0) {
            result = new ArrayList<PromotionData>(rows);
        }

        for (int i = 1; i <= rows; i++) {
            XSSFRow xssfRow = xSSFSheet.getRow(i);
            PromotionData pd = this.packBean(xssfRow, employee);
            result.add(pd);
        }
        return result;
    }

    ///18
    private PromotionData packBean(XSSFRow xssfRow, Employee employee) {
        PromotionData pd = new PromotionData();
        try {

            String vName = xssfRow.getCell(0).getStringCellValue().trim();
            String gName = xssfRow.getCell(1).getStringCellValue().trim();
            PromotionDataTree pdt = promotionDataService.getByName(gName);
            if (pdt.getParent().getName().equals(vName)) {
                pd.setVenueTree(pdt);
            } else {
                pd.setVenueTree(promotionDataService.getRoot());
            }

            try {
                BigDecimal outerId = new BigDecimal(xssfRow.getCell(2).getNumericCellValue());
                pd.setOuterId(outerId.toString());
            } catch (Exception e) {
                log.error(e.getMessage()+"id数据格式错误,采用字符串");
                pd.setOuterId(xssfRow.getCell(2).getStringCellValue());
            }
            BigDecimal ecId = new BigDecimal(xssfRow.getCell(3).getNumericCellValue());
            pd.setEcId(ecId.longValue());

            pd.setProductName(xssfRow.getCell(4) + "");
            pd.setListPrice(new BigDecimal(xssfRow.getCell(5) + ""));
            pd.setSalePrice(new BigDecimal(xssfRow.getCell(6) + ""));
            pd.setDiscount(new BigDecimal(xssfRow.getCell(7) + ""));
            pd.setProductUrl(xssfRow.getCell(8) + "");
            pd.setBuyLink(xssfRow.getCell(9) + "");
            pd.setFavoriteLink(xssfRow.getCell(10) + "");

            int weekSales = ((int) xssfRow.getCell(11).getNumericCellValue());
            pd.setWeekSales(weekSales);
            int monthSales = ((int) xssfRow.getCell(12).getNumericCellValue());
            pd.setMonthSales(monthSales);
            int totalSales = ((int) xssfRow.getCell(13).getNumericCellValue());
            pd.setTotalSales(totalSales);

            pd.setImgUrl(xssfRow.getCell(14) + "");
            pd.setIntroduction(xssfRow.getCell(15) + "");

            int index = ((int) xssfRow.getCell(16).getNumericCellValue());
            pd.setIndex(index);

            int stock = (int) xssfRow.getCell(17).getNumericCellValue();
            pd.setInventory(stock);
            pd.setCreateTime(new Date());
            pd.setOperation(employee.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            errorXSSFRowList.add(xssfRow);
        }
        return pd;
    }

}
