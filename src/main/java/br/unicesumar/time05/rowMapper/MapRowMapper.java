package br.unicesumar.time05.rowmapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MapRowMapper implements RowMapper<Map<String, Object>> {

    public MapRowMapper() {
    }

    @Override
    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> retorno = new HashMap<>();
        try {
            final ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                retorno.put(metaData.getColumnLabel(i).toLowerCase(), rs.getObject(i));
            }
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return retorno;
    }
    
}
