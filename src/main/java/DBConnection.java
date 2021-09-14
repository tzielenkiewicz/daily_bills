import java.sql.*;
import java.time.LocalDate;

public class DBConnection {
        private String JDBC_DRIVER;
        private String DB_URL;

        private String USER;
        private String PASSWORD;

        public DBConnection (String JDBC_DRIVER, String DB_URL, String USER, String PASSWORD){
            this.JDBC_DRIVER = JDBC_DRIVER;
            this.DB_URL = DB_URL;
            this.USER = USER;
            this.PASSWORD = PASSWORD;
        }


        public String getJDBC_DRIVER() {
            return JDBC_DRIVER;
        }

        public String getDB_URL() {
            return DB_URL;
        }

        public String getUSER() {
            return USER;
        }

        public String getPASSWORD() {
            return PASSWORD;
        }

        public static Connection connectionProcedure () {
            String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
            String DB_URL = "jdbc:mysql://localhost/bills";
            String USER = "root";
            String PASSWORD = "Tomeczek";
            Connection conn = null;
            try {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("------------------");
                System.out.println("Connection failed!");
                System.out.println("------------------");
            }
            return conn;
        }

        public static void createNewCheck(Bill newBill, Statement stmt) {

            try {
                stmt.executeUpdate("INSERT INTO checks VALUES(null, '" + newBill.getDate() + "', '"
                        + newBill.getPieczywo() + "', '" + newBill.getNabial() + "', '"
                        + newBill.getProduktyZbo() + "', '" + newBill.getMieso() + "', '" +
                        newBill.getWedlina() + "', '" + newBill.getWarzywaOwoce() + "', '" +
                        newBill.getSlodycze() + "', '" + newBill.getRyby() + "', '" + newBill.getDania()
                        + "', '" + newBill.getChemia() + "', '" + newBill.getUbrania() + "', '" +
                        newBill.getZabawki() + "', '" + newBill.getAGD() + "', '" + newBill.getMedycyna()
                        + "', '" + newBill.getLekarstwa() + "');");
            } catch (SQLException throwables) {
                System.out.println("------------------");
                System.out.println("Connection failed!");
                System.out.println("------------------");
            }
        }

        public static Bill collectLastMonthSummary(LocalDate today) {
            Connection conn = DBConnection.connectionProcedure();
            Bill lastMonthBill = new Bill();
            int lastMonth = today.minusMonths(1).getMonthValue();

            Statement stmt = null;

            try {
                stmt = conn.createStatement();
                String sql;

                sql = "SELECT SUM(pieczywo), SUM(nabiał), SUM(produktyZbo), SUM(mięso), SUM(wędlina), SUM(warzywaOwoce), " +
                        "SUM(słodycze), SUM(ryby), SUM(daniaGotowe), SUM(chemia), SUM(ubrania), SUM(zabawki), SUM(AGD), " +
                        "SUM(medycyna), SUM(lekarstwaSuplementy) FROM checks WHERE MONTH(date) = " + lastMonth;
                ResultSet rs = stmt.executeQuery(sql);
                float pieczywo, nabial, produktyZbo, mieso, wedlina, warzywaOwoce, slodycze, ryby, dania, chemia, ubrania,
                        zabawki, agd, medycyna, lekarstwa;

                LocalDate dateOfSummary = today.minusDays(today.getDayOfMonth());

                while (rs.next()) {
                    pieczywo = rs.getFloat("SUM(pieczywo)");
                    nabial = rs.getFloat("SUM(nabiał)");
                    produktyZbo = rs.getFloat("SUM(produktyZbo)");
                    mieso = rs.getFloat("SUM(mięso)");
                    wedlina = rs.getFloat("SUM(wędlina)");
                    warzywaOwoce = rs.getFloat("SUM(warzywaOwoce)");
                    slodycze = rs.getFloat("SUM(słodycze)");
                    ryby = rs.getFloat("SUM(ryby)");
                    dania = rs.getFloat("SUM(daniaGotowe)");
                    chemia = rs.getFloat("SUM(chemia)");
                    ubrania = rs.getFloat("SUM(ubrania)");
                    zabawki = rs.getFloat("SUM(zabawki)");
                    agd = rs.getFloat("SUM(AGD)");
                    medycyna = rs.getFloat("SUM(medycyna)");
                    lekarstwa = rs.getFloat("SUM(lekarstwaSuplementy)");

                    lastMonthBill = new Bill(dateOfSummary, pieczywo, nabial, produktyZbo, mieso, wedlina, warzywaOwoce, slodycze,
                            ryby, dania, chemia, ubrania, zabawki, agd, medycyna, lekarstwa);

                }

                rs.close();
                stmt.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return lastMonthBill;
        }
        public static Bill collectLastWeekSummary(LocalDate today) {
            Connection conn = DBConnection.connectionProcedure();
            Bill lastWeekBill = new Bill();
            LocalDate minusWeek = today.minusDays(7);
            LocalDate yesterday = today.minusDays(1);
            Statement stmt = null;

            try {
                stmt = conn.createStatement();
                String sql;

                sql = "SELECT SUM(pieczywo), SUM(nabiał), SUM(produktyZbo), SUM(mięso), SUM(wędlina), SUM(warzywaOwoce), " +
                        "SUM(słodycze), SUM(ryby), SUM(daniaGotowe), SUM(chemia), SUM(ubrania), SUM(zabawki), SUM(AGD), " +
                        "SUM(medycyna), SUM(lekarstwaSuplementy) FROM checks WHERE date BETWEEN '" + minusWeek + "' AND '"
                + yesterday + "'";
                ResultSet rs = stmt.executeQuery(sql);

                float pieczywo, nabial, produktyZbo, mieso, wedlina, warzywaOwoce, slodycze, ryby, dania, chemia, ubrania,
                        zabawki, agd, medycyna, lekarstwa;

                while (rs.next()) {
                    pieczywo = rs.getFloat("SUM(pieczywo)");
                    nabial = rs.getFloat("SUM(nabiał)");
                    produktyZbo = rs.getFloat("SUM(produktyZbo)");
                    mieso = rs.getFloat("SUM(mięso)");
                    wedlina = rs.getFloat("SUM(wędlina)");
                    warzywaOwoce = rs.getFloat("SUM(warzywaOwoce)");
                    slodycze = rs.getFloat("SUM(słodycze)");
                    ryby = rs.getFloat("SUM(ryby)");
                    dania = rs.getFloat("SUM(daniaGotowe)");
                    chemia = rs.getFloat("SUM(chemia)");
                    ubrania = rs.getFloat("SUM(ubrania)");
                    zabawki = rs.getFloat("SUM(zabawki)");
                    agd = rs.getFloat("SUM(AGD)");
                    medycyna = rs.getFloat("SUM(medycyna)");
                    lekarstwa = rs.getFloat("SUM(lekarstwaSuplementy)");

                    lastWeekBill = new Bill(today, pieczywo, nabial, produktyZbo, mieso, wedlina, warzywaOwoce, slodycze,
                            ryby, dania, chemia, ubrania, zabawki, agd, medycyna, lekarstwa);

                }

                rs.close();
                float suma = lastWeekBill.getPieczywo() + lastWeekBill.getNabial() + lastWeekBill.getLekarstwa() +
                        lastWeekBill.getWedlina() + lastWeekBill.getAGD() + lastWeekBill.getZabawki() +
                        lastWeekBill.getUbrania() + lastWeekBill.getProduktyZbo() + lastWeekBill.getMedycyna() +
                        lastWeekBill.getChemia() +lastWeekBill.getDania() + lastWeekBill.getRyby() +
                        lastWeekBill.getSlodycze() + lastWeekBill.getWarzywaOwoce() + lastWeekBill.getMieso();
                stmt.executeUpdate("INSERT INTO lastWeekSummary VALUES(null, '" + lastWeekBill.getDate() + "', '"
                        + suma + "', '" + lastWeekBill.getPieczywo() + "', '" + lastWeekBill.getNabial() + "', '"
                        + lastWeekBill.getProduktyZbo() + "', '" + lastWeekBill.getMieso() + "', '" +
                        lastWeekBill.getWedlina() + "', '" + lastWeekBill.getWarzywaOwoce() + "', '" +
                        lastWeekBill.getSlodycze() + "', '" + lastWeekBill.getRyby() + "', '" + lastWeekBill.getDania()
                        + "', '" + lastWeekBill.getChemia() + "', '" + lastWeekBill.getUbrania() + "', '" +
                        lastWeekBill.getZabawki() + "', '" + lastWeekBill.getAGD() + "', '" + lastWeekBill.getMedycyna()
                        + "', '" + lastWeekBill.getLekarstwa() + "');");
                stmt.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return lastWeekBill;
        }
}
