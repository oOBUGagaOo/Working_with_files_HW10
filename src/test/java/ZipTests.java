import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.InvalidArgumentException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipTests {

    private final ClassLoader cl = ZipTests.class.getClassLoader();
    String archiveName = "HomeWork10.zip";

    private ZipInputStream getStreamFromArchive(String archiveName, String filename) throws IOException {
        ZipEntry entry;
        ZipInputStream zis;
        InputStream is = cl.getResourceAsStream("HomeWork10.zip");
        zis = new ZipInputStream(is);
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().endsWith(filename)) return zis;
        }
        is.close();
        zis.close();
        throw new InvalidArgumentException("ERROR: File " + filename + " was not found in the archive" + archiveName + "\n");
    }


    @Test
    void zipWithPdfStreamTest() throws IOException {

        InputStream inputStream = getStreamFromArchive("HomeWork10.zip", "Locators_table.pdf");

        PDF pdf = new PDF(inputStream);

        System.out.println(pdf.author);

        Assertions.assertEquals(
                "Michael Sorens",
                pdf.author
        );

        inputStream.close();
    }

    @Test
    void zipWithXlsStreamTest() throws IOException, CsvException {

        InputStream inputStream = getStreamFromArchive("HomeWork10.zip", "teachers.xls");

        XLS xls = new XLS(inputStream);
        String value = xls.excel.getSheetAt(0).getRow(3).getCell(2).getStringCellValue();
        Assertions.assertTrue(value
                .length() > 0
        );
    }


    @Test
    void zipWithCSVStreamTest() throws IOException, CsvException {

        InputStream inputStream = getStreamFromArchive("HomeWork10.zip", "qaguru.csv");

        InputStreamReader isr = new InputStreamReader(inputStream);
        CSVReader csvReader = new CSVReader(isr);
        List<String[]> content = csvReader.readAll();
        Assertions.assertArrayEquals(new String[]{"Тучс", "JUnit5"}, content.get(1));
        inputStream.close();

    }


}

