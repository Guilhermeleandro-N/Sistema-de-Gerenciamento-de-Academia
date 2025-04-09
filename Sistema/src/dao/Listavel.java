package dao;

import java.sql.SQLException;
import java.util.List;

public interface Listavel<T> {
    List<T> listarCompletos() throws SQLException;
}
