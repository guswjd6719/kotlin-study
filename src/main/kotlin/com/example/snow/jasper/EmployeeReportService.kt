package com.example.snow.jasper

import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.JasperReport
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import net.sf.jasperreports.engine.export.JRPdfExporter
import net.sf.jasperreports.engine.util.JRLoader
import net.sf.jasperreports.export.SimpleExporterInput
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput
import net.sf.jasperreports.export.SimplePdfExporterConfiguration
import net.sf.jasperreports.export.SimplePdfReportConfiguration
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class EmployeeReportService (
    private val reportRepository: ReportRepository
){
    @Transactional(readOnly = true)
    fun makeReport(): String {

        return try {
            val employees = reportRepository.findAll()
            val reportPath = "/jasper"

            // 1-1) .jrxml 파일로 report 생성
            //val employeeReportStream = javaClass.getResourceAsStream("/jasper/template2.jrxml")
            //val jasperReport = JasperCompileManager.compileReport(employeeReportStream)

            // Compile the Jasper report from .jrxml to .japser
            //JRSaver.saveObject(jasperReport, "employeeReport2.jasper");

            // 1-2) 컴파일한 .jasper 파일로 report 생성
            val employeeReportStream = javaClass.getResourceAsStream("/jasper/employeeReport2.jasper")
            val jasperReport: JasperReport = JRLoader.loadObject(employeeReportStream) as JasperReport


            // Add parameters
            val parameters: MutableMap<String, Any> = HashMap()
            parameters["createdBy"] = "me"

            // Export the report to a PDF file
            val exporter = JRPdfExporter()
            val jasperPrint: JasperPrint = JasperFillManager.fillReport(
                jasperReport, parameters, JRBeanCollectionDataSource(employees)
            )

            exporter.setExporterInput(SimpleExporterInput(jasperPrint))
            exporter.setExporterOutput(
                SimpleOutputStreamExporterOutput("employeeReport3.pdf")
            )

            val reportConfig = SimplePdfReportConfiguration()
            reportConfig.isSizePageToContent = true
            reportConfig.isForceLineBreakPolicy = false

            val exportConfig = SimplePdfExporterConfiguration()
            exportConfig.metadataAuthor = "author"
            exportConfig.isEncrypted = false
            exportConfig.setAllowedPermissionsHint("PRINTING")
            exporter.setConfiguration(reportConfig)
            exporter.setConfiguration(exportConfig)

            exporter.exportReport()

            println("Done")
            "Report successfully generated @path= $reportPath"
        } catch (e: Exception) {
            e.printStackTrace()
            "Error--> check the console log"
        }
    }

}