package com.hiersun.oohdear.util;/**
 * Created by liubaocheng on 2017/3/28.
 */
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

//import com.hiersun.ecommerce.core.exceptions.ExcelException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
/**
 * Description:
 * Author: liubaocheng
 * Create: 2017-03-28 17:01
 **/
public class ExcelUtils {
    /**
     * 我要索引做字符串
     */
    public static final String Index = "@index";

    /**
     * @see
     * @param list 数据
     * @param fieldMap 字段映射，比如"id"(key)->"编号"(value)
     * @param sheetName 页面名字
     * @param sheetSize 每个页面数据行数（0~60000）
     * @param response 响应
     * @param inter 拦截器，用于自定义实现
     * @param parser 自定义字符串转换器，为空则调用toString
     * @throws Exception
     */
    public static <T> void listToExcelAddition(List<T> list,LinkedHashMap<String, String> fieldMap,
                                               String sheetName, int sheetSize, HttpServletResponse response, ExcelWriteInterceptor inter, StringParser parser) throws Exception {

        /*if(CollectionUtils.isEmpty(list)){
            throw new ExcelException("数据源中没有任何数据");
        }*/
        if (sheetSize > 60000 || sheetSize < 0) {
            sheetSize = 2000;
        }

        WritableWorkbook wwb;
        try {
            //文件名默认设置为当前时间：年月日时分秒
            String fileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
            //设置response头信息
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition","attachment;filename="+fileName+".xls");

            //创建工作簿并发送到OutputStream指定的地方
            wwb = Workbook.createWorkbook(response.getOutputStream());

            //因为2003的Excel一个工作表最多可以有65536条记录，除去列头剩下65535条
            //所以如果记录太多，需要放到多个工作表中，其实就是个分页的过程
            //1.计算一共有多少个工作表
            int sheetNum = (int)Math.ceil(list.size()/(double)sheetSize);
            //2.创建相应的工作表,并向其中填充数据
            for (int i=0;i<sheetNum;i++){
                WritableSheet sheet = wwb.createSheet(sheetName+i, i);
                int beginRow = inter.before(sheet, i);
                int firstIndex = i * sheetSize;
                int lastIndex = sheetSize + firstIndex -1;
                if (i == sheetNum - 1) {//是最后一页
                    lastIndex = list.size() - 1;
                }
                //向工作表中填充数据
                fillSheet(sheet, list, fieldMap, firstIndex, lastIndex, beginRow, parser);
                inter.after(sheet, i, beginRow + lastIndex - firstIndex + 1);
            }
            if (wwb.getNumberOfSheets() == 0) {
                wwb.createSheet(sheetName, 0);
            }
            wwb.write();
            wwb.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @see
     * @param list 数据
     * @param fieldMap 字段映射，比如"id"(key)->"编号"(value)
     * @param sheetName 页面名字
     * @param sheetSize 每个页面数据行数（0~60000）
     * @param response 响应
     * @param inter 拦截器，用于自定义实现
     * @throws Exception
     */
    public static <T> void listToExcelAddition(List<T> list,LinkedHashMap<String, String> fieldMap,
                                               String sheetName, int sheetSize, HttpServletResponse response, ExcelWriteInterceptor inter) throws Exception {
        listToExcelAddition(list, fieldMap, sheetName, sheetSize, response, inter, null);
    }

    /**
     * @MethodName : fillSheet
     * @Description : 向工作表中填充数据
     * @param sheet 工作表
     * @param list 数据源数据
     * @param fieldMap 中英文属性对照关系map
     * @param firstIndex 开始索引
     * @param lastIndex 结束索引
     * @param beginRow 开始行
     * @param <E>
     * @throws Exception
     */
    private static <E> void fillSheet(
            WritableSheet sheet,
            List<E> list,
            LinkedHashMap<String, String> fieldMap,
            int firstIndex,
            int lastIndex,
            int beginRow
    )throws Exception{
        fillSheet(sheet, list, fieldMap, firstIndex, lastIndex, beginRow, null);
    }

    /**
     * @MethodName  : listToExcel
     * @Description : 导出Excel（可以导出到本地文件系统，也可以导出到浏览器，可自定义工作表大小）
     * @param list      数据源
     * @param fieldMap  类的英文属性和Excel中的中文列名的对应关系 例：{id=编号}
     * 如果需要的是引用对象的属性，则英文属性使用类似于EL表达式的格式
     * 如：list中存放的都是student，student中又有college属性，而我们需要学院名称，则可以这样写
     * fieldMap.put("college.collegeName","学院名称")
     * @param sheetName 工作表的名称
     * @param sheetSize 每个工作表中记录的最大个数
     * @param os       导出流
     */
    public static <T> void listToExcel(List<T> list, LinkedHashMap<String, String> fieldMap, String sheetName, int sheetSize, OutputStream os) {

       /* if(CollectionUtils.isEmpty(list)){
            throw new ExcelException("数据源中没有任何数据");
        }*/

        if (sheetSize < 1 || sheetSize > 65535){
            sheetSize = 65535;
        }

        WritableWorkbook wwb;
        try {
            //创建工作簿并发送到OutputStream指定的地方
            wwb = Workbook.createWorkbook(os);

            //因为2003的Excel一个工作表最多可以有65536条记录，除去列头剩下65535条
            //所以如果记录太多，需要放到多个工作表中，其实就是个分页的过程
            //1.计算一共有多少个工作表
            double sheetNum = Math.ceil(list.size()/new Integer(sheetSize).doubleValue());
            //2.创建相应的工作表,并向其中填充数据
            for (int i=0;i<sheetNum;i++){
                //如果只有一个工作表的情况
                if (sheetNum == 1){
                    WritableSheet sheet = wwb.createSheet(sheetName, i);
                    //向工作表中填充数据
                    fillSheet(sheet, list, fieldMap, 0, list.size()-1);

                    //有多个工作表的情况
                } else {
                    WritableSheet sheet = wwb.createSheet(sheetName+(i+1),i);
                    //获取开始索引和结束索引
                    int firstIndex = i*sheetSize;
                    int lastIndex = (i+1)*sheetSize-1>list.size()-1 ? list.size()-1 : (i+1)*sheetSize-1;
                    //填充工作表
                    fillSheet(sheet, list, fieldMap, firstIndex, lastIndex);
                }
            }
            if (wwb.getNumberOfSheets() == 0) {
                wwb.createSheet(sheetName, 0);
            }
            wwb.write();
            wwb.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * @MethodName  : listToExcel
     * @Description : 导出Excel（可以导出到本地文件系统，也可以导出到浏览器，工作表大小为2003支持的最大值）
     * @param list      数据源
     * @param fieldMap      类的英文属性和Excel中的中文列名的对应关系
     * @param sheetName 工作表的名称
     * @param os       导出流
     */
    public static <T> void listToExcel(List<T> list, LinkedHashMap<String, String> fieldMap, String sheetName, OutputStream os){
        listToExcel(list, fieldMap, sheetName, 65535, os);
    }

    /**
     * @MethodName  : listToExcel
     * @Description : 导出Excel（导出到浏览器，可以自定义工作表的大小）
     * @param list      数据源
     * @param fieldMap      类的英文属性和Excel中的中文列名的对应关系
     * @param sheetName 工作表的名称
     * @param sheetSize    每个工作表中记录的最大个数
     * @param response  使用response可以导出到浏览器
     */
    public static <T> void listToExcel(List<T> list, LinkedHashMap<String, String> fieldMap, String sheetName, int sheetSize, HttpServletResponse response){
        //文件名默认设置为当前时间：年月日时分秒
        String fileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        //设置response头信息
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition","attachment;filename="+fileName+".xls");

        //创建工作簿并发送到浏览器
        try{
            OutputStream os = response.getOutputStream();
            listToExcel(list, fieldMap, sheetName, os);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @MethodName  : listToExcel
     * @Description : 导出Excel（导出到浏览器，工作表的大小是2003支持的最大值）
     * @param list      数据源
     * @param fieldMap      类的英文属性和Excel中的中文列名的对应关系
     * @param sheetName 工作表的名称
     * @param response  使用response可以导出到浏览器
     */
    public static <T>  void   listToExcel (List<T> list , LinkedHashMap<String,String> fieldMap, String sheetName, HttpServletResponse response) {
        listToExcel(list, fieldMap, sheetName, 65535, response);
    }

    /**
     * @MethodName : fillSheet
     * @Description : 向工作表中填充数据
     * @param sheet 工作表
     * @param list 数据源数据
     * @param fieldMap 中英文属性对照关系map
     * @param firstIndex 开始索引
     * @param lastIndex 结束索引
     * @param <E>
     * @throws Exception
     */
    private static <E> void fillSheet(WritableSheet sheet, List<E> list, LinkedHashMap<String, String> fieldMap, int firstIndex, int lastIndex)throws Exception{
        fillSheet(sheet, list, fieldMap, firstIndex, lastIndex, 0);
    }

    /**
     * @MethodName  : getFieldValueByNameSequence
     * @Description :
     * 根据带路径或不带路径的属性名获取属性值
     * 即接受简单属性名，如userName等，又接受带路径的属性名，如student.department.name等
     *
     * @param fieldNameSequence  带路径的属性名或简单属性名
     * @param o 对象
     * @return  属性值
     * @throws Exception
     */
    private static Object getFieldValueByNameSequence(String fieldNameSequence, Object o) throws Exception{
        Object value = null;
        //将fieldNameSequence进行拆分
        String[] attributes = fieldNameSequence.split("\\.");
        if (attributes.length == 1){
            value = getFieldValueByName(fieldNameSequence, o);
        }else {
            //根据属性名获取属性对象
            Object fieldObj = getFieldValueByName(attributes[0], o);
            String subFieldNameSequence = fieldNameSequence.substring(fieldNameSequence.indexOf(".")+1);
            value = getFieldValueByNameSequence(subFieldNameSequence, fieldObj);
        }

        return value;
    }

    /**
     * @MethodName  : getFieldValueByName
     * @Description : 根据字段名获取字段值
     * @param fieldName 字段名
     * @param o 对象
     * @return  字段值
     */
    private static Object getFieldValueByName(String fieldName, Object o) throws Exception{
        Object value = null;
        Field field = getFieldByName(fieldName, o.getClass());

        if (field != null){
            field.setAccessible(true);
            value = field.get(o);
        }else {
            throw new RuntimeException(o.getClass().getSimpleName() + "类不存在字段名" +fieldName);
        }

        return value;
    }

    /**
     * @MethodName  : getFieldByName
     * @Description : 根据字段名获取字段
     * @param fieldName 字段名
     * @param clazz 包含该字段的类
     * @return 字段
     */
    private static Field getFieldByName(String fieldName, Class<?> clazz){
        //拿到本类所有的字段
        Field[] selfFields = clazz.getDeclaredFields();

        //如果本类中存放该字段，则返回
        for (Field field : selfFields){
            if (field.getName().equals(fieldName)){
                return field;
            }
        }

        //否则，查看父类中是否存在此字段，如果有则返回
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && superClazz != Object.class){
            return getFieldByName(fieldName, superClazz);
        }

        //如果本来和父类都没有该字段，则返回null
        return null;
    }


    /**
     * @MethodName  : setColumnAutoSize
     * @Description : 设置工作表自动列宽和首行加粗
     * @param ws
     */
    private static void setColumnAutoSize(WritableSheet ws,int extraWith){
        //获取本列的最宽单元格的宽度
        for (int i=0;i<ws.getColumns();i++){
            int colWith = 0;
            for (int j=0;j<ws.getRows();j++){
                String content = ws.getCell(i,j).getContents().toString();
                int cellWith = content.length();
                if (colWith < cellWith){
                    colWith = cellWith;
                }
            }
            //设置单元格的宽度为最宽宽度+额外宽度
            ws.setColumnView(i, colWith+extraWith);
        }
    }

    /**
     * @Description 将Excel转化成实体对象List
     * @param is            要导入Excel的输入流
     * @param sheetName     导入的工作表名称
     * @param entityClass   List中对象的类型（Excel中的每一行都要转化为该类型的对象）
     * @param fieldMap      类的英文属性和Excel中的中文列名的对应关系 例：{id=编号}
     * @param uniqueFields  指定业务主键组合（即复合主键），这些列的组合不能重复
     * @return List
     */
    public static<T> List<T> excelToList(InputStream is, String sheetName, Class<T> entityClass, LinkedHashMap<String, String> fieldMap, String[] uniqueFields){
        //定义要返回的list
        List<T> resultList = new ArrayList<T>();

        try {
            //根据excel数据源创建WorkBook
            Workbook wb = Workbook.getWorkbook(is);

            //获取工作表
            Sheet sheet = wb.getSheet(sheetName);

            //获取工作表的有效行数
            int realRows = 0 ;
            for (int i=0;i<sheet.getRows();i++){

                int nullCols = 0;
                for (int j=0;j<sheet.getColumns();j++){
                    Cell CurrentCell = sheet.getCell(j, i);
                    if (CurrentCell == null || "".equals(CurrentCell.getContents().toString())){
                        nullCols++;
                    }
                }

                if (nullCols == sheet.getColumns()){
                    break;
                }else {
                    realRows++;
                }
            }

            //如果Excel中没有任何数据则提示错误信息
            if (realRows <= 1){
                throw new RuntimeException("Excel文件中没有任何数据");
            }

            Cell[] firstRow = sheet.getRow(0);
            String[] excelFieldNames = new String[firstRow.length];
            //获取Excel的列名
            for (int i=0;i<firstRow.length;i++){
                excelFieldNames[i] = firstRow[i].getContents().toString().trim();
            }
            //判断需要的字段在Excel中是否都存在
            boolean isExist = true;
            List<String> excelFieldList = Arrays.asList(excelFieldNames);
            for (String cnName : fieldMap.values()){
                if (!excelFieldList.contains(cnName)){
                    isExist = false;
                    break;
                }
            }

            //如果有列名不存在或不匹配，则抛出异常并提示错误
            if (!isExist){
                throw new RuntimeException("Excel中缺少必要的字段，或字段名称有误");
            }

            //将列名和列号放入Map中，这样通过列名就可以拿到列号
            LinkedHashMap<String, Integer> colMap = new LinkedHashMap<String, Integer>();
            for (int i=0;i<excelFieldNames.length;i++){
                colMap.put(excelFieldNames[i], firstRow[i].getColumn());
            }

            //判断是否有重复行
            //1.获取uniqueFields指定的列
            Cell[][] uniqueCells = new Cell[uniqueFields.length][];
            for (int i=0;i<uniqueFields.length;i++){
                int col = colMap.get(uniqueFields[i]);
                uniqueCells[i] = sheet.getColumn(col);
            }
            //2.从指定列中寻找重复行
            for (int i=1;i<realRows;i++){
                int nullCols = 0;
                int length = uniqueFields.length;
                for (int j=0;j<length;j++){
                    Cell currentCell = uniqueCells[j][i];
                    String currentContent = currentCell.getContents().toString().trim();
                    Cell sameCell = sheet.findCell(currentContent,
                            currentCell.getColumn(),
                            currentCell.getRow()+1,
                            currentCell.getColumn(),
                            uniqueCells[j][realRows-1].getRow(),
                            true);
                    if (sameCell != null){
                        nullCols++;
                    }
                }
                //复合主键，意味着这些列的组合不能重复，
                // 只有当所有的列都有重复的时候，才被认为是有重复行
                if (nullCols == length){
                    throw new Exception("Excel中有重复行，请检查");
                }
            }

            //将sheet转换为list
            for (int i=1;i<realRows;i++){
                //新建要转换的对象
                T entity = entityClass.newInstance();

                //给对象中的字段赋值
                for (Map.Entry<String, String> entry : fieldMap.entrySet()){
                    //获取英文字段名
                    String enNormalName = entry.getKey();
                    //获取中文字段名
                    String cnNormalName = entry.getValue();
                    //根据中文字段名获取列号
                    int col = colMap.get(cnNormalName);

                    //获取当前单元格中的内容
                    String content = sheet.getCell(col, i).getContents().toString().trim();

                    //给对象赋值
                    setFieldValueByName(enNormalName, content, entity);
                }

                resultList.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    /**
     * @Description 根据字段名给对象的字段赋值
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @param o          对象
     */
    private static void setFieldValueByName(
            String fieldName,Object fieldValue,Object o)throws Exception{
        Field field = getFieldByName(fieldName, o.getClass());
        if (field != null){
            field.setAccessible(true);
            //获取字段类型
            Class<?> fieldType = field.getType();

            //根据字段类型给字段赋值
            if (String.class == fieldType){
                field.set(o, String.valueOf(fieldValue));
            } else if (Integer.TYPE == fieldType || Integer.class == fieldType){
                field.set(o, Integer.valueOf(fieldValue.toString()));
            } else if (Long.TYPE == fieldType || Long.class == fieldType){
                field.set(o, Long.valueOf(fieldValue.toString()));
            } else if (Float.TYPE == fieldType || Float.class == fieldType){
                field.set(o, Float.valueOf(fieldValue.toString()));
            } else if (Short.TYPE == fieldType || Short.class == fieldType){
                field.set(o, Short.valueOf(fieldValue.toString()));
            } else if (Double.TYPE == fieldType || Double.class == fieldType){
                field.set(o, Double.valueOf(fieldValue.toString()));
            } else if (Character.TYPE == fieldType){
                if ((fieldValue != null) && (fieldValue.toString().length() > 0)){
                    field.set(o, Character.valueOf(fieldValue.toString().charAt(0)));
                }
            } else if (Date.class == fieldType){
                field.set(o, DateUtils.parseDate(fieldValue.toString(),new String[]{"yyyy-MM-dd HH:mm:ss"}));
            } else {
                field.set(o, fieldValue);
            }
        } else {
            throw new RuntimeException(o.getClass().getSimpleName() + "类不存在字段名" + fieldName);
        }
    }

    /**
     * @MethodName : fillSheet
     * @Description : 向工作表中填充数据
     * @param sheet 工作表
     * @param list 数据源数据
     * @param fieldMap 中英文属性对照关系map
     * @param firstIndex 开始索引
     * @param lastIndex 结束索引
     * @param beginRow 开始行
     * @param parser 字符串转换工具
     * @throws Exception
     */
    private static <E> void fillSheet(
            WritableSheet sheet,
            List<E> list,
            LinkedHashMap<String, String> fieldMap,
            int firstIndex,
            int lastIndex,
            int beginRow,
            StringParser parser
    )throws Exception{
        //定义存放英文字段名和中文字段名的数组
        int size = fieldMap.size();
        String[] enFields = new String[size];
        String[] cnFields = new String[size];

        //填充数组
        int count = 0;
        for (Map.Entry<String, String> entry:fieldMap.entrySet()){
            enFields[count] = entry.getKey();
            cnFields[count] = entry.getValue();
            count++;
        }

        //填充表头
        for (int i=0;i<cnFields.length;i++){
            Label label = new Label(i, beginRow ,cnFields[i]);
            sheet.addCell(label);
        }

        //填充内容
        int rowNo = 1+beginRow;
        for (int index=firstIndex;index<=lastIndex;index++){
            E item = list.get(index);
            for (int i=0;i<enFields.length;i++){
                if (Index.equals(enFields[i])) {
                    //设置索引作为输出字符串
                    String fieldValue = String.valueOf(index+1);
                    Label label = new Label(i, rowNo, fieldValue);
                    sheet.addCell(label);
                    continue;
                }
                Object objValue = getFieldValueByNameSequence(enFields[i], item);
                String fieldValue = "";
                if (parser != null) {
                    fieldValue = parser.parse(objValue, enFields[i]);
                } else {
                    fieldValue = objValue==null ? "" : objValue.toString();
                }
                Label label = new Label(i, rowNo, fieldValue);
                sheet.addCell(label);
            }
            rowNo++;
        }
        //设置自动列宽
        setColumnAutoSize(sheet, 5);
    }

    public interface ExcelWriteInterceptor {
        /**
         * 写数据之前
         * @param sheet 当前页，为空sheet
         * @param sheetNum 当前是第几页
         * @return 从第几行开始写数据
         */
        public int before(WritableSheet sheet, int sheetNum);
        /**
         * 写数据之后
         * @param sheet 当前页，已写完数据
         * @param sheetNum 当前是第几页
         * @param rowNum 当前写到了多少行
         */
        public void after(WritableSheet sheet, int sheetNum, int rowNum);
    }

    @FunctionalInterface
    public interface StringParser {
        /**
         * 将对象转换成字符串
         * @param obj 对象
         * @param propName 属性名
         * @return
         */
        public String parse(Object obj, String propName);
    }
}
