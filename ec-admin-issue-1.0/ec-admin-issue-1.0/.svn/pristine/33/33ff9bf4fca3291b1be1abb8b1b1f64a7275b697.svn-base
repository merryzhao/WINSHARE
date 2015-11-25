package com.winxuan.ec.admin.controller.order;

import java.util.Date;

/**
 * 快递对帐
 * @author juqkai(juqkai@gmail.com)
 */
public class ExpressToAcccountForm {
    private static final Long MAX_DAY = 5270400000l;
    //开始时间
    private Date startDate;
    //结束时间
    private Date endDate;
    //快递公司
    private Long delivery;
    //运单号所在列
    private Integer codeCol;
    //订单信息开始列
    private Integer startCol;
    
    
//    public void testExpressToAcccount(){
//        File file = new File("D:/temp/ec/CCES文轩2012.1月.xls");
//        Workbook excel = null;
//        WritableWorkbook write = null;
//        try{
//            excel = Workbook.getWorkbook(file);
//            write = Workbook.createWorkbook(file, excel);
//        }catch (Exception e){
//        }
//        if(excel != null){
//            Sheet sheet = excel.getSheet(0);
//            WritableSheet sh = write.getSheet(0);
//            for(int i = 0; i < sheet.getRows(); i++){
//                String count = sheet.getCell(0, i).getContents();
//                try {
//                    sh.addCell(new Label(8, i, count));
//                } catch (RowsExceededException e) {
//                } catch (WriteException e) {
//                } catch (IndexOutOfBoundsException e) {
//                }
//            }
//        }
//    }
//    
//    public static void main(String[] args) {
//        ExpressToAcccountForm express = new ExpressToAcccountForm();
//        express.testExpressToAcccount();
//    }
    
    
    
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Integer getCodeCol() {
        return codeCol;
    }

    public void setCodeCol(Integer codeCol) {
        this.codeCol = codeCol;
    }

    public Integer getStartCol() {
        return startCol;
    }

    public void setStartCol(Integer startCol) {
        this.startCol = startCol;
    }

    public Long getDelivery() {
        return delivery;
    }

    public void setDelivery(Long delivery) {
        this.delivery = delivery;
    }

    public boolean canAvailable() throws ExpressToAccountException {
        if(this.delivery == null){
            throw new ExpressToAccountException("必需选择快递公司!");
        }
        if(this.startDate == null){
            throw new ExpressToAccountException("必需选择开始时间!");
        }
        if(this.endDate == null){
            throw new ExpressToAccountException("必需选择结束时间!");
        }
        if(endDate.getTime() - this.startDate.getTime() > MAX_DAY){
            throw new ExpressToAccountException("时间范围最大不能超过两个月!");
        }
        if(codeCol == null || this.codeCol < 1){
            throw new ExpressToAccountException("'运单号在第几列'不能小于1!");
        }
        if(startCol == null || this.startCol < codeCol){
            throw new ExpressToAccountException("'订单填充到第几列'不能小于'运单号在第几列'否则会导致数据覆盖!");
        }
        return true;
    }
    
    /**
     * 
     * @author juqkai(juqkai@gmail.com)
     */
    public static class ExpressToAccountException extends Exception{
        private static final long serialVersionUID = 1L;
        public ExpressToAccountException(){
            super();
        }
        public ExpressToAccountException(String string) {
            super(string);
        }
        
    }
}
