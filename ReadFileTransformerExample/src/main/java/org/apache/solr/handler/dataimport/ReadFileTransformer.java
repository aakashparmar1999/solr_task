package org.apache.solr.handler.dataimport;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class ReadFileTransformer extends Transformer {

    @Override
    public Object transformRow(Map<String, Object> row, Context context) {

        List<Map<String, String>> fields = context.getAllEntityFields();
        for (Map<String, String> field : fields) {

            String trim = field.get("FULL_TEXT_1");

                String columnName = field.get(DataImporter.COLUMN);

                Object filePath = row.get(columnName);

                if (filePath != null) {
                    try {
                        Path path = Paths.get(filePath.toString());
                        if (Files.exists(path) && !Files.isDirectory(path)) {
                            byte[] fileContent = Files.readAllBytes(path);
                            row.put(columnName, new String(fileContent,0,fileContent.length));
                        }
                    }catch (Exception e){

                    System.out.println(e);                    }

            }
        }
        return row;
    }   

}