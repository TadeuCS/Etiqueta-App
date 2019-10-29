/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcs.util;

import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.util.List;
import java.util.Map;
import javafx.scene.control.TextField;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Tadeu-PC
 */
public class OUtil {

    public static String geraCodigoBarras(Integer codigo) {
        String code = "2" + completaComZeros(codigo.toString(), 6) + completaComZeros("1", 5);
        return code + geraDigVerificador(code);
    }

    public static String completaComZeros(String codigo, int length) {
        while (codigo.length() < length) {
            codigo = "0" + codigo;
        }
        return codigo;
    }

    public static int geraDigVerificador(String code) {
        int val = 0;
        for (int i = 0; i < code.length(); i++) {
            val += ((int) Integer.parseInt(code.charAt(i) + "")) * ((i % 2 == 0) ? 1 : 3);
        }

        int checksum_digit = 10 - (val % 10);
        if (checksum_digit == 10) {
            checksum_digit = 0;
        }

        return checksum_digit;
    }

    public static void maxField(TextField textField, Integer length) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == null || newValue.length() > length) {
                textField.setText(oldValue);
            }
        }
        );
    }

    public static String onlyDigitsValue(TextField field, Integer length) {
        String result = field.getText();
        maxField(field, length);
        if (result == null) {
            return null;
        }
        return result.replaceAll("[^0-9]", "");
    }

    public static String fieldToUPPER(TextField field) {
        field.textProperty().addListener((ov, oldValue, newValue) -> {
            field.setText(newValue.toUpperCase());
        });
        return field.getText();
    }

    public static void imprimirDefault(Map map, List data, String dirRelatorio, Boolean preVisualiza) {
        try {
            //compilação do JRXML
//            JasperReport report = JasperCompileManager.compileReport(caminhoDoRelatorio);

            //preenchimento do relatório
            //JRBeanCollectionDataSource 
            JasperPrint print = JasperFillManager.fillReport(dirRelatorio, map, new JRBeanCollectionDataSource(data));

            if (print == null) {
                MensagemUtils.erro("Falha ao criar o relatório");
            } else {
                // verifica se tem alguma página  
                if (print.getPages().isEmpty()) {
                    MensagemUtils.erro("Não há conteúdo no relatório. A visualização foi cancelada");
                } else {
                    if (preVisualiza) {
                        JasperViewer jv = new JasperViewer(print, false);
                        jv.setVisible(true);
                    } else {
                        JasperPrintManager.printReport(print, false);
                    }
                }
            }

            //exportar pra pdf
            //            JasperExportManager.exportReportToPdfFile(print, "src/Relatorios/RelatorioEmPDF.pdf");
        } catch (JRException e) {
            MensagemUtils.erro("Erro ao gerar relatório!");
            e.printStackTrace();
        }
    }

    public static void imprimir(Map map, List data, String nomeImpressora, String dirRelatorio) {
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(dirRelatorio, map, new JRBeanCollectionDataSource(data));
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
            printerJob.defaultPage(pageFormat);
            int selectedService = 0;
            AttributeSet attributeSet = new HashPrintServiceAttributeSet(new PrinterName(nomeImpressora, null));
            PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, attributeSet);
            try {
                printerJob.setPrintService(printService[selectedService]);
            } catch (Exception e) {
                System.out.println(e);
            }
            JRPrintServiceExporter exporter;
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
            printRequestAttributeSet.add(new Copies(1));

            // these are deprecated
            exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService[selectedService]);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService[selectedService].getAttributes());
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            exporter.exportReport();
//            JasperPrintManager.printReport(jasperPrint, false);
        } catch (JRException ex) {
            if (ex.getMessage().contains("PrinterAbortException")) {
                System.err.println("Cancelamento de impressão!");
            } else if (ex.getMessage().contains("Invalid name of PrintService")) {
                MensagemUtils.erro("A impressora " + nomeImpressora + " não foi encontrada!\nNão foi impresso seu pedido.");
            } else if (ex.getMessage().contains("java.io.FileNotFoundException")) {
                MensagemUtils.erro("Esta faltando o arquivo " + dirRelatorio + " na pasta de comprovantes!");
            } else {
                MensagemUtils.exception("Erro ao emitir as etiquetas!", ex);
            }
        }
    }

    public static PrintService[] listPrinters() {
        return PrintServiceLookup.lookupPrintServices(null, null);
    }

    private static PrintService findPrintService(String printerName) {
        PrintService output = null;
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            if (printService.getName().trim().equalsIgnoreCase(printerName)) {
                output = printService;
                break;
            }
        }
        return output;
    }

    private static PrintService DefaultPrintService() {
        return PrintServiceLookup.lookupDefaultPrintService();
    }
}
