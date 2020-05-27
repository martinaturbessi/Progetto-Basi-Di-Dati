package DB;

import controller.ImportController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportFileQuery {
    private DataSource dataSource;
    private ImportController ic = new ImportController();

    public ImportFileQuery() {

        dataSource = new DataSource();
    }

    public boolean puntiContorniImport(String path0) throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        final String create = "CREATE TABLE IF NOT EXISTS punti_contorni_filamenti_temp( "
                + "\"IDFIL\" integer,"
                + "\"GLON_CONT\" double precision,"
                + "\"GLAT_CONT\" double precision);";

        final String copy = "COPY punti_contorni_filamenti_temp FROM '" + path0 + "' DELIMITER ',' CSV HEADER;";

        final String attempt = "DELETE FROM punti_contorni_filamenti_temp WHERE (\"GLON_CONT\", \"GLAT_CONT\") IN " +
                "(SELECT \"GLON_CONT\", \"GLAT_CONT\" FROM punti_contorni_filamenti);";
        final String insert = "INSERT INTO punti_contorni_filamenti SELECT DISTINCT ON" +
                "(\"GLON_CONT\", \"GLAT_CONT\") \"IDFIL\", \"GLON_CONT\", \"GLAT_CONT\"" +
                "FROM punti_contorni_filamenti_temp;";

        final String update = "UPDATE punti_contorni_filamenti SET "
                + "\"IDFIL\" = punti_contorni_filamenti_temp.\"IDFIL\", "
                + "\"GLON_CONT\" = punti_contorni_filamenti_temp.\"GLON_CONT\", "
                + "\"GLAT_CONT\" = punti_contorni_filamenti_temp.\"GLAT_CONT\""
                + "FROM punti_contorni_filamenti_temp WHERE punti_contorni_filamenti.\"GLON_CONT\" = punti_contorni_filamenti_temp.\"GLON_CONT\" AND punti_contorni_filamenti.\"GLAT_CONT\" = punti_contorni_filamenti_temp.\"GLAT_CONT\";";

        final String drop = "DROP TABLE punti_contorni_filamenti_temp;";

        final String query = create + "\n" + copy + "\n" + attempt + "\n" + insert + "\n" + update + "\n" + drop;

        System.out.println(query);
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // release resources
            if (result != null) {
                result.close();
            }
            // release resources
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public boolean filamentiImport(String path1) throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        final String create = "CREATE TABLE IF NOT EXISTS filamenti_temp( "
                + "\"IDFIL\" integer,"
                + "\"NAME\" character varying ,"
                + "\"TOTAL_FLUX\" double precision,"
                + "\"MEAN_DENS\" double precision,"
                + "\"MEAN_TEMP\" double precision,"
                + "\"ELLIPTICITY\" double precision,"
                + "\"CONTRAST\" double precision,"
                + "\"SATELLITE\" character varying,"
                + "\"INSTRUMENT\" character varying);";


        final String copy = "COPY filamenti_temp FROM '" + path1 + "' DELIMITER ',' CSV HEADER;";
        final String attempt = "DELETE FROM filamenti_temp WHERE (\"IDFIL\") IN " +
                "(SELECT \"IDFIL\" FROM filamenti);";
        final String insert = "INSERT INTO filamenti SELECT DISTINCT ON" +
                "(\"IDFIL\") \"IDFIL\", \"NAME\", \"TOTAL_FLUX\", \"MEAN_DENS\", \"MEAN_TEMP\", \"ELLIPTICITY\", \"CONTRAST\", \"SATELLITE\", \"INSTRUMENT\"" +
                "FROM filamenti_temp;";
        //final String insert = "INSERT INTO filamenti SELECT DISTINCT ON (\"IDFIL\")* FROM filamenti_temp WHERE (filamenti_temp.\"IDFIL\", filamenti_temp.\"NAME\", filamenti_temp.\"TOTAL_FLUX\", filamenti_temp.\"MEAN_DENS\", filamenti_temp.\"MEAN_TEMP\", filamenti_temp.\"ELLIPTICITY\", filamenti_temp.\"CONTRAST\", filamenti_temp.\"SATELLITE\", filamenti_temp.\"INSTRUMENT\") NOT IN ( SELECT * FROM filamenti);";
        final String update = "UPDATE filamenti SET "
                + "\"IDFIL\" = filamenti_temp.\"IDFIL\","
                + "\"NAME\" = filamenti_temp.\"NAME\","
                + "\"TOTAL_FLUX\" = filamenti_temp.\"TOTAL_FLUX\","
                + "\"MEAN_DENS\" = filamenti_temp.\"MEAN_DENS\","
                + "\"MEAN_TEMP\" = filamenti_temp.\"MEAN_TEMP\","
                + "\"ELLIPTICITY\" = filamenti_temp.\"ELLIPTICITY\","
                + "\"CONTRAST\" = filamenti_temp.\"CONTRAST\","
                + "\"SATELLITE\" = filamenti_temp.\"SATELLITE\","
                + "\"INSTRUMENT\" = filamenti_temp.\"INSTRUMENT\""
                + "FROM filamenti_temp WHERE filamenti.\"IDFIL\" = filamenti_temp.\"IDFIL\";";

        final String drop = "DROP TABLE filamenti_temp;";

        final String query = create + "\n" + copy + "\n" + attempt + "\n" + insert + "\n" + update + "\n" + drop;

        System.out.println(query);
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // release resources
            if (result != null) {
                result.close();
            }
            // release resources
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public boolean puntiSegmentiImport(String path2) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        final String create = "CREATE TABLE IF NOT EXISTS punti_segmenti_temp( "
                + "\"IDFIL\" integer,"
                + "\"IDBRANCH\" integer,"
                + "\"TYPE\" character varying,"
                + "\"GLON_BR\" double precision,"
                + "\"GLAT_BR\" double precision,"
                + "\"N\" integer,"
                + "\"FLUX\" double precision);";

        final String copy = "COPY punti_segmenti_temp FROM '" + path2 + "' DELIMITER ',' CSV HEADER;";
        final String attempt = "DELETE FROM punti_segmenti_temp WHERE (\"GLON_BR\", \"GLAT_BR\") IN " +
                "(SELECT \"GLON_BR\", \"GLAT_BR\" FROM punti_segmenti);";
        final String insert = "INSERT INTO punti_segmenti SELECT DISTINCT ON" +
                "(\"GLON_BR\", \"GLAT_BR\") \"IDFIL\", \"IDBRANCH\", \"GLON_BR\", \"GLAT_BR\", \"N\", \"FLUX\"" +
                "FROM punti_segmenti_temp;";
        //final String insert = "INSERT INTO scheletro SELECT DISTINCT ON (\"GLON_BR\", \"GLAT_BR\")* FROM scheletro_temp WHERE (scheletro_temp.\"IDFIL\", scheletro_temp.\"IDBRANCH\", scheletro_temp.\"TYPE\", scheletro_temp.\"GLON_BR\", scheletro_temp.\"GLAT_BR\", scheletro_temp.\"N\", scheletro_temp.\"FLUX\") NOT IN ( SELECT * FROM scheletro);";
        final String update = "UPDATE punti_segmenti SET "
                + "\"IDFIL\" = punti_segmenti_temp.\"IDFIL\","
                + "\"IDBRANCH\" = punti_segmenti_temp.\"IDBRANCH\","
                + "\"GLON_BR\" = punti_segmenti_temp.\"GLON_BR\","
                + "\"GLAT_BR\" = punti_segmenti_temp.\"GLAT_BR\","
                + "\"N\" = punti_segmenti_temp.\"N\","
                + "\"FLUX\" = punti_segmenti_temp.\"FLUX\""
                + "FROM punti_segmenti_temp WHERE punti_segmenti.\"GLON_BR\" = punti_segmenti_temp.\"GLON_BR\" AND punti_segmenti.\"GLAT_BR\" = punti_segmenti_temp.\"GLAT_BR\";";

        final String drop = "DROP TABLE punti_segmenti_temp;";

        final String query = create + "\n" + copy + "\n" + attempt + "\n" + insert + "\n" + update + "\n" + drop;

        System.out.println(query);
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // release resources
            if (result != null) {
                result.close();
            }
            // release resources
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public boolean segmentiImport(String path3) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        final String create = "CREATE TABLE IF NOT EXISTS segmenti_temp( "
                + "\"IDFIL\" integer,"
                + "\"IDBRANCH\" integer,"
                + "\"TYPE\" character varying,"
                + "\"GLON_BR\" double precision,"
                + "\"GLAT_BR\" double precision,"
                + "\"N\" integer,"
                + "\"FLUX\" double precision);";

        final String copy = "COPY segmenti_temp FROM '" + path3 + "' DELIMITER ',' CSV HEADER;";
        final String attempt = "DELETE FROM segmenti_temp WHERE (\"IDFIL\", \"IDBRANCH\") IN " +
                "(SELECT \"IDFIL\", \"IDBRANCH\" FROM segmenti);";
        final String insert = "INSERT INTO segmenti SELECT DISTINCT ON" +
                "(\"IDFIL\", \"IDBRANCH\") \"IDFIL\", \"IDBRANCH\", \"TYPE\"" +
                "FROM segmenti_temp;";
        //final String insert = "INSERT INTO scheletro SELECT DISTINCT ON (\"GLON_BR\", \"GLAT_BR\")* FROM scheletro_temp WHERE (scheletro_temp.\"IDFIL\", scheletro_temp.\"IDBRANCH\", scheletro_temp.\"TYPE\", scheletro_temp.\"GLON_BR\", scheletro_temp.\"GLAT_BR\", scheletro_temp.\"N\", scheletro_temp.\"FLUX\") NOT IN ( SELECT * FROM scheletro);";
        final String update = "UPDATE segmenti SET "
                + "\"IDFIL\" = segmenti_temp.\"IDFIL\","
                + "\"IDBRANCH\" = segmenti_temp.\"IDBRANCH\","
                + "\"TYPE\" = segmenti_temp.\"TYPE\""
                + "FROM segmenti_temp WHERE segmenti.\"IDFIL\" = segmenti_temp.\"IDFIL\" AND segmenti.\"IDBRANCH\" = segmenti_temp.\"IDBRANCH\";";

        final String drop = "DROP TABLE segmenti_temp;";

        final String query = create + "\n" + copy + "\n" + attempt + "\n" + insert + "\n" + update + "\n" + drop;

        System.out.println(query);
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // release resources
            if (result != null) {
                result.close();
            }
            // release resources
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public boolean stelleImport(String path4) throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        final String create = "CREATE TABLE IF NOT EXISTS stelle_temp( "
                + "\"IDSTAR\" integer,"
                + "\"NAMESTAR\" character varying ,"
                + "\"GLON_ST\" double precision,"
                + "\"GLAT_ST\" double precision,"
                + "\"FLUX_ST\" double precision,"
                + "\"TYPE_ST\" character varying);";


        final String copy = "COPY stelle_temp FROM '" + path4 + "' DELIMITER ',' CSV HEADER;";

        final String attempt = "DELETE FROM stelle_temp WHERE (\"IDSTAR\") IN " +
                "(SELECT \"IDSTAR\" FROM stelle);";
        final String insert = "INSERT INTO stelle SELECT DISTINCT ON" +
                "(\"IDSTAR\") \"IDSTAR\", \"NAMESTAR\", \"GLON_ST\", \"GLAT_ST\", \"FLUX_ST\", \"TYPE_ST\"" +
                "FROM stelle_temp;";
        //final String insert = "INSERT INTO stelle SELECT DISTINCT ON (\"IDSTAR\")* FROM stelle_temp WHERE (stelle_temp.\"IDSTAR\", stelle_temp.\"NAMESTAR\", stelle_temp.\"GLON_ST\", stelle_temp.\"GLAT_ST\", stelle_temp.\"FLUX_ST\", stelle_temp.\"TYPE_ST\") NOT IN ( SELECT * FROM stelle);";
        final String update = "UPDATE stelle SET "
                + "\"IDSTAR\" = stelle_temp.\"IDSTAR\","
                + "\"NAMESTAR\" = stelle_temp.\"NAMESTAR\","
                + "\"GLON_ST\" = stelle_temp.\"GLON_ST\","
                + "\"GLAT_ST\" = stelle_temp.\"GLAT_ST\","
                + "\"FLUX_ST\" = stelle_temp.\"FLUX_ST\","
                + "\"TYPE_ST\" = stelle_temp.\"TYPE_ST\""
                + "FROM stelle_temp WHERE stelle.\"IDSTAR\" = stelle_temp.\"IDSTAR\";";

        final String drop = "DROP TABLE stelle_temp;";

        final String query = create + "\n" + copy + "\n" + insert + "\n" + update + "\n" + drop;

        System.out.println(query);
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // release resources
            if (result != null) {
                result.close();
            }
            // release resources
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
