package com.rabbiter.oes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.entity.BpjPerson;
import com.rabbiter.oes.serviceimpl.AdminServiceImpl;
import com.rabbiter.oes.util.ApiResultHandler;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;


/**
 * 管理端功能
 * 重置用户密码为初始密码
 * 查询得分情况
 * 导出得分情况表
 */

@RestController
public class AdminController {

    private AdminServiceImpl adminService;
    @Autowired
    public AdminController(AdminServiceImpl adminService){
        this.adminService = adminService;
    }

    /**
     * 管理员输入8位员工编号重置用户密码
     * @param userId
     * @return
     */
    @GetMapping("/admin/{userId}")
    public ApiResult updatePWD1(@PathVariable("userId") String userId){
        int a = adminService.updatePWD1(userId);
        if(a == 0){
            return ApiResultHandler.buildApiResult(10000,"8为员工编号不存在",null);
        }
        return ApiResultHandler.success(a);
    }

    /**
     * 分页查询所有的分情况
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/admin1/{page}/{size}")
    public ApiResult selectAll1(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        System.out.println("分页查询所有被评价人员得分情况");
        ApiResult apiResult;
        Page<BpjPerson> bpjPersonPage = new Page<>(page,size);
        IPage<BpjPerson> all = adminService.selectAllPage(bpjPersonPage);
        List<BpjPerson> bpjPersonList = all.getRecords();
        for(BpjPerson bp : bpjPersonList){
            if(bp.getSelfevaluation() != null && bp.getSelfevaluation().equals("0")){
                bp.setSelfevaluation("");
            }
            if(bp.getSuperior() != null && bp.getSuperior().equals("0")){
                bp.setSuperior("");
            }
            if(bp.getSuperiorNm() != null && bp.getSuperiorNm().equals("0")){
                bp.setSuperiorNm("");
            }
            if(bp.getEqual() != null && bp.getEqual().equals("0")){
                bp.setEqual("");
            }
            if(bp.getEqualNm() != null && bp.getEqualNm().equals("0")){
                bp.setEqualNm("");
            }
            if(bp.getSubordinate() != null && bp.getSubordinate().equals("0")){
                bp.setSubordinate("");
            }
            if(bp.getSubordinateNm() != null && bp.getSubordinateNm().equals("0")){
                bp.setSubordinateNm("");
            }
            if(bp.getTotalscore() != null && bp.getTotalscore().equals("0")){
                bp.setTotalscore("");
            }
            if(bp.getTotalNm() != null && bp.getTotalNm().equals("0")){
                bp.setTotalNm("");
            }
        }
        apiResult = ApiResultHandler.buildApiResult(200,"请求成功",all);
        return apiResult;
    }

    /**
     * 管理员根据姓名查询得分信息
     * @param name
     * @return
     */
    @GetMapping("/admin1/{name}")
    public ApiResult selectAll1(@PathVariable("name") String name){
        System.out.println("查询所有被评价人员得分情况");
        List<BpjPerson> bpjPersonList = adminService.selectAll1(name);
        for(int i = 0;i < bpjPersonList.size();i++){
            BpjPerson bp = bpjPersonList.get(i);
            if(bp.getSelfevaluation() != null && bp.getSelfevaluation().equals("0")){
                bp.setSelfevaluation("");
            }
            if(bp.getSuperior() != null && bp.getSuperior().equals("0")){
                bp.setSuperior("");
            }
            if(bp.getSuperiorNm() != null && bp.getSuperiorNm().equals("0")){
                bp.setSuperiorNm("");
            }
            if(bp.getEqual() != null && bp.getEqual().equals("0")){
                bp.setEqual("");
            }
            if(bp.getEqualNm() != null && bp.getEqualNm().equals("0")){
                bp.setEqualNm("");
            }
            if(bp.getSubordinate() != null && bp.getSubordinate().equals("0")){
                bp.setSubordinate("");
            }
            if(bp.getSubordinateNm() != null && bp.getSubordinateNm().equals("0")){
                bp.setSubordinateNm("");
            }
            if(bp.getTotalscore() != null && bp.getTotalscore().equals("0")){
                bp.setTotalscore("");
            }
            if(bp.getTotalNm() != null && bp.getTotalNm().equals("0")){
                bp.setTotalNm("");
            }
        }
        return ApiResultHandler.success(bpjPersonList);
    }

    /**
     * 管理员导出查询结果
     * @param response
     */
    @GetMapping("/download")
    public void downloadExcel(HttpServletResponse response) {
        System.out.println("管理员导出查询结果");
        List<BpjPerson> bpjPersonList = adminService.selectAll();
        BpjPerson bp = new BpjPerson();
        XSSFWorkbook hs = new XSSFWorkbook();
        System.out.println("生成excle");
        XSSFSheet sheet = hs.createSheet("shee1");
        XSSFRow headerRow = sheet.createRow(0);
        XSSFCell cell = headerRow.createCell(0);
        //设置字段名
        headerRow.createCell(0).setCellValue("姓名");
        headerRow.createCell(1).setCellValue("8位员工编号");
        headerRow.createCell(2).setCellValue("uass编号");
        headerRow.createCell(3).setCellValue("自评得分");
        headerRow.createCell(4).setCellValue("上级评价得分");
        headerRow.createCell(5).setCellValue("上级评价人数");
        headerRow.createCell(6).setCellValue("同级评价得分");
        headerRow.createCell(7).setCellValue("同级评价人数");
        headerRow.createCell(8).setCellValue("下级评价得分");
        headerRow.createCell(9).setCellValue("下级评价人数");
        headerRow.createCell(10).setCellValue("总得分");
        headerRow.createCell(11).setCellValue("总评价人数");
        int rowNum = 1;
        for (int i = 0; i < bpjPersonList.size(); i++) {
            bp = bpjPersonList.get(i);
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(bp.getName());
            row.createCell(1).setCellValue(bp.getId());
            row.createCell(2).setCellValue(bp.getUass());
            row.createCell(3).setCellValue(bp.getSelfevaluation());
            row.createCell(4).setCellValue(bp.getSuperior());
            row.createCell(5).setCellValue(bp.getSuperiorNm());
            row.createCell(6).setCellValue(bp.getEqual());
            row.createCell(7).setCellValue(bp.getEqualNm());
            row.createCell(8).setCellValue(bp.getSubordinate());
            row.createCell(9).setCellValue(bp.getSubordinateNm());
            row.createCell(10).setCellValue(bp.getTotalscore());
            row.createCell(11).setCellValue(bp.getTotalNm());
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);
        sheet.autoSizeColumn(9);
        sheet.autoSizeColumn(10);
        sheet.autoSizeColumn(11);
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename" + URLEncoder.encode("resoult.xlsx","UTF-8"));
            response.setCharacterEncoding("utf-8");
            hs.write(outputStream);
            outputStream.flush();
            outputStream.close();
            hs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
