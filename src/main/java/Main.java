import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        byte option, product;
        LocalDate today = LocalDate.now();
        LocalDate date;
        float pieczywo, nabial, chemia, ubrania, produktyZbozowe, zabawki, mieso, wedlina, warzywaOwoce, AGD,
                ryby, dania, slodycze, lekarstwa, medycyna;
        Scanner choice = new Scanner(System.in);

        do {

            System.out.println("Wybierz opcję:");
            System.out.println("1: Nowy rachunek");
            System.out.println("2: Podsumowanie ubiegłego miesiąca");
            System.out.println("3: Podsumowanie ostatnich siedmiu dni");
            System.out.println("4: Koniec programu");

            option = collectByte(choice);
            if (option > 4 || option < 1) System.out.println("Wybierz od 1 do 4!");

            switch (option) {
                case 1 -> {
                    pieczywo = 0;
                    nabial = 0; chemia = 0;
                    ubrania = 0;
                    produktyZbozowe = 0;
                    zabawki = 0;
                    mieso = 0;
                    wedlina = 0;
                    warzywaOwoce = 0;
                    AGD = 0;
                    ryby = 0;
                    dania = 0;
                    slodycze = 0;
                    medycyna = 0;
                    lekarstwa = 0;

                    Scanner count = new Scanner(System.in);

                    do {
                        date = null;
                        System.out.print("Podaj datę w formacie (RRRR-MM-DD): ");
                        String dateS = count.nextLine();

                        try {
                            date = LocalDate.parse(dateS);
                        } catch (Exception throwables) {
                            System.out.println("Nieprawidłowy format daty!");
                        }

                    } while (date == null);
                    do {
                        product = 0;
                        System.out.println("Podaj kategorię: ");
                        System.out.println("1: Pieczywo, 2: Nabiał, 3: Produkty zbożowe, 4: Mięso, 5: Wędlina,");
                        System.out.println("6: Warzywa i owoce, 7: Słodycze, 8: Ryby, 9: Dania gotowe,");
                        System.out.println("10: Chemia, 11: Ubrania, 12: Zabawki, 13: AGD");
                        System.out.println("14: Medycyna, 15: Lekarstwa i suplementy");
                        System.out.println();
                        System.out.println("20: Zakończ i zapisz do bazy danych");

                        try {
                             product = choice.nextByte();
                        } catch (InputMismatchException e) {
                            System.out.println("Nieprawidłowa wartość! Za karę kończę program!");
                            System.exit(0);
                        }


                        switch (product) {
                            case 20 -> {
                                Bill newBill = new Bill(date, pieczywo, nabial, produktyZbozowe, mieso, wedlina,
                                        warzywaOwoce, slodycze, ryby, dania, chemia, ubrania, zabawki, AGD, medycyna, lekarstwa);
                                System.out.println("Zapisuję do bazy danych...");
                                System.out.println("Dnia " + newBill.getDate() +" wydano na: ");
                                showSummary(newBill, "zakupu");
                                try {
                                    Connection conn = DBConnection.connectionProcedure();
                                    Statement stmt = conn.createStatement();
                                    DBConnection.createNewCheck(newBill, stmt);

                                } catch (SQLException throwables) {
                                    System.out.println("--------------------------");
                                    System.out.println("Statement creation failed!");
                                    System.out.println("--------------------------");
                                }
                            }
                            case 1 -> {
                                System.out.print("Podaj kwotę: ");
                                pieczywo += collectNumber(count);
                            }
                            case 2 -> {
                                System.out.print("Podaj kwotę: ");
                                nabial += count.nextFloat();
                            }
                            case 3 -> {
                                System.out.print("Podaj kwotę: ");
                                produktyZbozowe += count.nextFloat();
                            }
                            case 4 -> {
                                System.out.print("Podaj kwotę: ");
                                mieso += count.nextFloat();
                            }
                            case 5 -> {
                                System.out.print("Podaj kwotę: ");
                                wedlina += count.nextFloat();
                            }
                            case 6 -> {
                                System.out.print("Podaj kwotę: ");
                                warzywaOwoce += count.nextFloat();
                            }
                            case 7 -> {
                                System.out.print("Podaj kwotę: ");
                                slodycze += count.nextFloat();
                            }
                            case 8 -> {
                                System.out.print("Podaj kwotę: ");
                                ryby += count.nextFloat();
                            }
                            case 9 -> {
                                System.out.print("Podaj kwotę: ");
                                dania += count.nextFloat();
                            }
                            case 10 -> {
                                System.out.print("Podaj kwotę: ");
                                chemia += count.nextFloat();
                            }
                            case 11 -> {
                                System.out.print("Podaj kwotę: ");
                                ubrania += count.nextFloat();
                            }
                            case 12 -> {
                                System.out.print("Podaj kwotę: ");
                                zabawki += count.nextFloat();
                            }
                            case 13 -> {
                                System.out.print("Podaj kwotę: ");
                                AGD += count.nextFloat();
                            }
                            case 14 -> {
                                System.out.print("Podaj kwotę: ");
                                medycyna += count.nextFloat();
                            }
                            case 15 -> {
                                System.out.print("Podaj kwotę: ");
                                lekarstwa += count.nextFloat();
                            }
                        }
                    } while (product != 20);
                }

                case 2 -> {
                    Bill summary = DBConnection.collectLastMonthSummary(today);
                    showSummary(summary, "miesiąca");

                }
                case 3 -> {
                    Bill summary = DBConnection.collectLastWeekSummary(today);
                    showSummary(summary, "tygodnia");

                }

                case 4 -> {
                    System.out.println("Koniec programu...");

                }
            }
        } while (option !=4);
    }

    private static void sendMail(float sum) {
        String text;
        if (sum > 700) text = "Wydajesz zbyt wiele ostatnimi czasy! Spróbuj przez następnych " +
                "kilka dni ograniczyć wydatki do najważniejszych artykułów pierwszej potrzeby...";
        else text = "Brawo, wygląda na to, że trzymasz wydatki pod kontrolą :-) Oby tak dalej!";

            String to = "tu wpisz adres mailowy do żony";
            String hs = "tu wpisz hasło do swojego gmaila";
            String from = "tu wpisz adres swojego gmaila";
            String host = "smtp.gmail.com";
            int port = 465;

            Properties properties = new Properties();
            properties.put("mail.transport.protocol", "smtps");
            properties.put("mail.smtps.auth", "true");

            javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties);

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Podsumowanie tygodniowych wydatków");
                message.setText("W ciągu ostatniego tygodnia wydaliście " + Math.round(sum) + " zł. " + text);
                Transport transport = session.getTransport();
                transport.connect(host, port, from, hs);
                transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
                transport.close();
                System.out.println("-------------------------------");
                System.out.println("== E-mail sent successfully! ==");
                System.out.println("-------------------------------");
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }
        }


    private static byte collectByte(Scanner choice) {
        byte number = 0;
        try {
            number = choice.nextByte();
        } catch (InputMismatchException e) {
            System.out.println("Nieprawidłowa wartość!");
            System.exit(0);
        }
        return number;
    }

    private static float collectNumber(Scanner count) {
        float number = 0;
        try {
            number = count.nextFloat();
        } catch (InputMismatchException e) {
            System.out.println("Nieprawidłowa wartość!");
        }
        return number;
    }

    private static void showSummary(Bill summary, String okres) {

        float sum = summary.getPieczywo() + summary.getLekarstwa() + summary.getChemia() + summary.getSlodycze() +
                summary.getMedycyna() + summary.getAGD() + summary.getZabawki() + summary.getUbrania() +
                summary.getDania() + summary.getRyby() + summary.getWarzywaOwoce() + summary.getWedlina() +
                summary.getMieso() + summary.getProduktyZbo() + summary.getNabial();
        System.out.println("W ciągu ostatniego " + okres + " wydano " +Math.round(sum)+ " zł, w tym na: ");
        System.out.printf("pieczywo: %.2f zł (%.2f procent), nabiał: %.2f zł (%.2f procent),",
                summary.getPieczywo(), 100*summary.getPieczywo()/sum, summary.getNabial(), 100*summary.getNabial()/sum);
        System.out.println();
        System.out.printf("produkty zbożowe %.2f zł (%.2f procent), mięso: %.2f zł (%.2f procent),",
                summary.getProduktyZbo(), 100*summary.getProduktyZbo()/sum, summary.getMieso(), 100*summary.getMieso()/sum);
        System.out.println();
        System.out.printf("wędlinę: %.2f zł (%.2f procent), warzywa i owoce: %.2f zł (%.2f procent),",
                summary.getWedlina(), 100*summary.getWedlina()/sum, summary.getWarzywaOwoce(), 100*summary.getWarzywaOwoce()/sum);
        System.out.println();
        System.out.printf("słodycze: %.2f zł (%.2f procent), ryby: %.2f zł (%.2f procent),",
                summary.getSlodycze(), 100*summary.getSlodycze()/sum, summary.getRyby(), 100*summary.getRyby()/sum);
        System.out.println();
        System.out.printf("dania gotowe: %.2f zł (%.2f procent), chemię: %.2f zł (%.2f procent),",
                summary.getDania(), 100*summary.getDania()/sum, summary.getChemia(), 100*summary.getChemia()/sum);
        System.out.println();
        System.out.printf("ubrania: %.2f zł (%.2f procent), zabawki: %.2f zł (%.2f procent),",
                summary.getUbrania(), 100*summary.getUbrania()/sum, summary.getZabawki(), 100*summary.getZabawki()/sum);
        System.out.println();
        System.out.printf("AGD: %.2f zł (%.2f procent), medycynę: %.2f zł (%.2f procent),",
                summary.getAGD(), 100*summary.getAGD()/sum, summary.getMedycyna(), 100*summary.getMedycyna()/sum);
        System.out.println();
        System.out.printf("lekarstwa i suplementy: %.2f zł (%.2f procent),", summary.getLekarstwa(), 100*summary.getLekarstwa()/sum);
        System.out.println();

        if (okres.equals("tygodnia"))  sendMail(sum);

    }
}
