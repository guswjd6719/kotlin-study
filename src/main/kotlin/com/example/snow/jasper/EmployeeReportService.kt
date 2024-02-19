package com.example.snow.jasper

import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import net.sf.jasperreports.engine.export.JRPdfExporter
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

            // Compile the Jasper report from .jrxml to .japser

            val employeeReportStream = javaClass.getResourceAsStream("/jasper/template2.jrxml")
            val jasperReport = JasperCompileManager.compileReport(employeeReportStream)
            //JRSaver.saveObject(jasperReport, "employeeReport2.jasper");

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
                SimpleOutputStreamExporterOutput("employeeReport2.pdf")
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