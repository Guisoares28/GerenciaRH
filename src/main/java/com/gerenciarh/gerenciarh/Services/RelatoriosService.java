package com.gerenciarh.gerenciarh.Services;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gerenciarh.gerenciarh.Models.User;
import com.gerenciarh.gerenciarh.Utils.RelatoriosUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.gerenciarh.gerenciarh.Exceptions.BusinessException;

@Service
public class RelatoriosService {

    public byte[] gerarPdf(List<User> users) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        users.sort((u1, u2) -> u2.getSalario().compareTo(u1.getSalario()));
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.addCell("Nome");
        table.addCell("Salario");
        try{
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph("Relatório de salarios por usuário"));
            document.add(new Paragraph(" "));
            for(User usuarios : users){
                table.addCell(usuarios.getName());
                table.addCell(usuarios.getSalario().toString());
            }
            table.addCell("Total");
            table.addCell(RelatoriosUtils.somarSalarios(users).toString());
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            throw new BusinessException("Erro ao gerar relatório", e);
        }
        return outputStream.toByteArray();
    }




}
