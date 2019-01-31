package akademia.phonebookapp.commons.xlsCreator;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CreatorXLS<T> {

    private Class<T> clazz;

    public CreatorXLS(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void createFile(List<T> series, String path, String fileName) throws NoSuchMethodException {

        //tworze obiekt reprezentujacy caly plik excel
        HSSFWorkbook workbook = new HSSFWorkbook();

        //tworze arkusz w pliku excel
        HSSFSheet sheet = workbook.createSheet(fileName);

        //ustawiam czcionki
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short)10);
        headerFont.setColor(IndexedColors.BLUE.getIndex());
        //zapisuje styl
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(headerFont);

        //kolekcja zawierająca odczytane nazwy pol klasy. to beda kolumny
        List<String> columns = new ArrayList<>();

        for (Field f : clazz.getDeclaredFields()) {
            columns.add(f.getName());
        }
        //zapisuje do struktury pliku pola klasy jako naglowki kolumn
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns.get(i));
            cell.setCellStyle(cellStyle); //zapisuje styl do komorki naglowka
        }


        //zapis danych i wywolywanie metod get
        for (int i = 0; i < series.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);

            for (int j = 0; j < columns.size(); j++) {
                HSSFCell cell = row.createCell(j);

                Method method = series.get(i)
                        .getClass()
                        .getMethod("get" + columns.get(j)
                                .substring(0, 1)
                                .toUpperCase()+columns.get(j)
                                .substring(1));
                Object result = null;
                try {
                    result = method.invoke(series.get(i));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                cell.setCellValue(String.valueOf(result));
            }
        }


        //tworzenie pliku docelowego
        long mills = System.currentTimeMillis();
        String file = path + fileName + "_" + mills + ".xls";

        try {
            workbook.write(new File(file));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
