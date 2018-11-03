import com.kopach.*;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
            showMenu(session);

        } finally {
            session.close();
        }
    }

    public static void showMenu(Session session) {
        System.out.println("  Menu:" +
                "\n1. Select all data with table" +
                "\n2. Insert data to table" +
                "\n3. Delete student in table" +
                "\n4. Update data in table" +
                "\n5. Select M:M(student : lecturer)" +
                "\n6. Insert and delete(M:M)(student : lecturer)" +
                "\nAnother number - Exit");

        Scanner scanner = new Scanner(System.in);

        switch (scanner.nextInt()) {
            case 1:
                selectDataWithAllTable(session);

                break;

            case 2:
                System.out.println(
                        "\n1. Insert student" +
                                "\n2. Insert group_of_students" +
                                "\nAnother number - Exit");
                switch (scanner.nextInt()) {
                    case 1:
                        insertStudent(session);
                        break;

                    case 2:
                        insertGroupOfStudent(session);
                        break;

                    default:
                        showMenu(session);
                        break;
                }
                break;

            case 3:
                deleteStudent(session);
                break;

            case 4:
                updateStudent(session);
                break;

            case 5:
                selectStudentAndLecturer(session);
                break;

            case 6:
                System.out.println(
                        "\n1. Insert data to M:M table" +
                                "\n2. Delete data in M:M table" +
                                "\nAnother number - Exit");

                switch (scanner.nextInt()) {
                    case 1:
                        insertDataToStudentAndLecturer(session);
                        break;

                    case 2:
                        deleteDataWithStudentAndLecturer(session);
                        break;

                    default:
                        showMenu(session);
                        break;
                }
                break;

            default:
                showMenu(session);
                break;
        }

        System.out.println("0 - Show menu" +
                "\nAnother number - Exit");

        switch (scanner.nextInt()) {
            case 0:
                showMenu(session);
                break;

            default:
                break;
        }
    }

    private static void selectDataWithAllTable(Session session) {
        Query query = session.createQuery("from " + "StudentEntity");
        System.out.format("%3s %-18s %-18s %-18s %s\n", "student_id", "last_name", "first_name", "name_of_group", "specialty");
        for (Object obj : query.list()) {
            StudentEntity studentEntity = (StudentEntity) obj;
            System.out.format("%3d %-18s %-18s %-18s %s\n", studentEntity.getStudentId(),
                    studentEntity.getLastName(), studentEntity.getFirstName(), studentEntity.getGroupOfStudentByNameOfGroup().getNameOfGroup(), studentEntity.getSpecialty());
        }
        System.out.println("\n");

        Query query_2 = session.createQuery("from " + "LecturerEntity");
        System.out.format("%3s %-18s %-18s %s\n", "ID", "first_name", "last_name", "degree");
        if (query_2.list() == null) {
            System.out.println("Query2 list = null");
        }
        for (Object obj : query_2.list()) {
            LecturerEntity book = (LecturerEntity) obj;
            System.out.format("%3d %-18s %-18s %s\n", book.getLecturerId(), book.getFirstName(), book.getLastName(), book.getDegree());
        }
        System.out.println("\n");

        Query query_3 = session.createQuery("from " + "GroupOfStudentEntity");
        for (Object obj : query_3.list()) {
            GroupOfStudentEntity groupOfStudentEntity = (GroupOfStudentEntity) obj;
            System.out.format("%s\n", groupOfStudentEntity.getNameOfGroup());
        }
        System.out.println("\n");

        System.out.println("Done");
    }

    private static void deleteStudent(Session session) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input student id:  ");
        Integer studentId = scanner.nextInt();

        StudentEntity studentEntity = session.load(StudentEntity.class, studentId);

        if (studentEntity != null) {
            session.beginTransaction();
            Query query = session.createQuery("delete StudentEntity where studentId=:student_id_code");
            query.setParameter("student_id_code", studentId);
            int result = query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Done");
        } else {
            System.out.println("Student does not exist");
        }
    }

    private static void updateStudent(Session session) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nInput a student id: ");
        int student_id = scanner.nextInt();
        System.out.println("Input new first name for student: ");
        String new_name = scanner.next();

        StudentEntity studentEntity = session.load(StudentEntity.class, student_id);
        if (studentEntity != null) {
            session.beginTransaction();
            Query query = session.createQuery("update StudentEntity set firstName=:new_name_code  where studentId = :student_id_code");
            query.setParameter("new_name_code", new_name);
            query.setParameter("student_id_code", student_id);
            int result = query.executeUpdate();
            session.getTransaction().commit();
        } else System.out.println("Student does not exist");

        System.out.println("Done");
    }

    private static void insertGroupOfStudent(Session session) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input a new name group: ");
        String newGroup = scanner.next();

        session.beginTransaction();
        GroupOfStudentEntity newGroupOfStudentEntity = new GroupOfStudentEntity(newGroup);
        session.save(newGroupOfStudentEntity);
        session.getTransaction().commit();

        System.out.println("Done");
    }

    public static void deleteDataWithStudentAndLecturer(Session session){
        session.beginTransaction();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input student id: ");
        int studentId = scanner.nextInt();
        System.out.println("Input lecturer id: ");
        int lecturerId = scanner.nextInt();

        Query query1 = session.createQuery("from StudentEntity  where studentId= :student_id_code");
        query1.setParameter("student_id_code", studentId);
        StudentEntity studentEntity = (StudentEntity) query1.list().get(0);
        Query query2 = session.createQuery("from LecturerEntity where lecturerId= :lecturer_id_code");
        query2.setParameter("lecturer_id_code", lecturerId);
        LecturerEntity lecturerEntity = (LecturerEntity) query2.list().get(0);
        studentEntity.deleteLecturerEntity(lecturerEntity);
        session.getTransaction().commit();
        System.out.println("Done");
    }

    private static void selectStudentAndLecturer(Session session) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input student id: ");
        Integer studentId = scanner.nextInt();


        Query query = session.createQuery("from StudentEntity where studentId=:student_id_code");
        query.setParameter("student_id_code", studentId);
        System.out.format("\nM:M --------------------\n");
        for (Object obj : query.list()) {
            StudentEntity studentEntity = (StudentEntity) obj;
            System.out.format("%3d %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %s\n", studentEntity.getStudentId(),
                    studentEntity.getLastName(), studentEntity.getFirstName(), studentEntity.getGroupOfStudentByNameOfGroup().getNameOfGroup(),
                    studentEntity.getSpecialty(), " - ", studentEntity.getLecturerEntities().get(0).getLecturerId(),
                    studentEntity.getLecturerEntities().get(0).getFirstName(), studentEntity.getLecturerEntities().get(0).getLastName(),
                    studentEntity.getLecturerEntities().get(0).getDegree());
        }

        System.out.println("Done");
    }

    public static void insertDataToStudentAndLecturer(Session session){
        session.beginTransaction();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input student id: ");
        Integer artistId = scanner.nextInt();
        System.out.println("Input lecturer id: ");
        Integer lecturerId = scanner.nextInt();

        Query query1 = session.createQuery("from StudentEntity  where studentId= :student_id_code");
        query1.setParameter("student_id_code", artistId);
        StudentEntity studentEntity = (StudentEntity) query1.list().get(0);
        Query query2 = session.createQuery("from LecturerEntity  where lecturerId= :lecturer_id_code");
        query2.setParameter("lecturer_id_code", lecturerId);
        LecturerEntity lecturerEntity = (LecturerEntity) query2.list().get(0);
        studentEntity.addLecturerEntity(lecturerEntity);
        session.getTransaction().commit();
        System.out.println("Done");
    }

    private static void insertStudent(Session session) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input new student last name: ");
        String last_name = scanner.next();
        System.out.println("Input new student first name: ");
        String first_name = scanner.next();
        System.out.println("Input the group for student: ");
        String group = scanner.next();
        System.out.println("Input new student specialty: ");
        String specialty = scanner.next();

        session.beginTransaction();
        StudentEntity studentEntity = new StudentEntity(last_name, first_name, specialty, new GroupOfStudentEntity(group));
        session.save(studentEntity);
        session.getTransaction().commit();
        System.out.println("Done");
    }

}