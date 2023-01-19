package uz.sh.Chapter2_InputAndOutPut;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.awt.geom.Point2D;
import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Date;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 12/6/22 11:12 AM
 **/
public class Chapter2 {
    public static void main(String[] args) {
        System.out.println("Hello world");
    }
}

@AllArgsConstructor
@NoArgsConstructor
class Manager extends Employee {

    static final long serialVersionUID = 42L;

    public Employee secretary;

    public Manager(String name, int age, java.util.Date date) {
        super(name, age, date);
    }

    //. . .
}

class Input_OutPutStreams {

//    InputStream and OutputStream are basic abstract classes ( uses bytes )
//    Reader and Write are basic abstract classes too ( uses chars )

    public static void main(String[] args) throws IOException {
        System.out.println("System.getProperty(\"user.dir\") = " + System.getProperty("user.dir"));//returns project working directory
//
//        FileInputStream fileInputStream = new FileInputStream("src/main/resources/numbers.zip");
//        var zin = new ZipInputStream(fileInputStream);
//        ZipEntry nextEntry = zin.getNextEntry();
//        System.out.println("nextEntry.getName() = " + nextEntry.getName());
//
//        Scanner scanner = new Scanner(zin);
//        while (scanner.hasNextLine()){
//            System.out.println(scanner.nextLine());
//        }

//        DataInputStream input = new DataInputStream(zin);

//        int count = zin.available();
//        byte[] ary = new byte[count];
//        int read = input.read(ary);
//        for (byte bt : ary) {
//            char k = (char) bt;
//            System.out.print(k + "-");
//        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);
        byteArrayOutputStream.writeBytes("3123123".getBytes());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        Scanner scanner1 = new Scanner(byteArrayInputStream);

        while ( scanner1.hasNextLine() ) {
            System.out.println(scanner1.nextLine());
        }
    }
}

class HowtoWriteTextOutput {
    public static void main(String[] args) throws FileNotFoundException {

        try ( var out = new PrintWriter("src/main/resources/users.txt", StandardCharsets.UTF_8) ) {

            String name = "Harry Hacker";
            double salary = 75000;
            out.print(name);
            out.print(' ');
            out.println(salary);

        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }

        var out = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream("employee.txt"), StandardCharsets.UTF_8),
                true); // autoflush


    }
}

class HowtoReadTextInput {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("src/main/resources/users.txt"));
        List<String> list = new ArrayList<>();
        while ( scanner.hasNext() ) {
            list.add(scanner.next());
        }
        list.forEach(System.out::println);

        System.out.println("------------------------------------------------------");

        var content = Files.readString(Path.of("src/main/resources/users.txt"));
        System.out.println(content.toString());


        System.out.println("------------------------------------------------------");

        List<String> lines = Files.readAllLines(Path.of("src/main/resources/users.txt"));

        System.out.println("------------------------------------------------------");

//        If the file is large, process the lines lazily as a Stream<String>:
//        try (Stream<String> lines = Files.lines(path, charset))
//        {
//. . .
//        }

    }
}


class RandomAccessFileExample {
    static final String FILEPATH = "src/main/resources/myFile.txt";

    public static void main(String[] args) {
        try {
            RandomAccessFile file = new RandomAccessFile(FILEPATH, "rw");
            file.write("REal Madrid the best".getBytes());
            System.out.println(file.getFilePointer());
            file.write("  Nimadr".getBytes());
            System.out.println(file.getFilePointer());
            file.seek(5);
            byte[] bytes = new byte[6];
            file.read(bytes);
            System.out.println(new String(bytes));
            System.out.println(file.getFilePointer());
            Thread.sleep(10000);
            file.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        } catch ( InterruptedException e ) {
            throw new RuntimeException(e);
        }
    }
}

class ZIPArchives {


    public static void main(String[] args) {


        try ( var zin = new ZipInputStream(new FileInputStream("src/main/resources/numbers.zip")) ) {
            ZipEntry entry;
            while ( (entry = zin.getNextEntry()) != null ) {
                System.out.println(entry.getName());
                zin.closeEntry();
            }
            ZipFile zipFile = new ZipFile("src/main/resources/numbers.zip");
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            Iterator<? extends ZipEntry> iterator = entries.asIterator();
            while ( iterator.hasNext() ) {
                ZipEntry next = iterator.next();
                InputStream inputStream = zipFile.getInputStream(next);

                byte[] bytes = inputStream.readAllBytes();
                String s = new String(bytes);
                System.out.println("s = " + s);

            }


//            BufferedInputStream bufferedInputStream = new BufferedInputStream(zin);
//
//            byte[] bytes = bufferedInputStream.readAllBytes();

        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }


//--------------------------------------------------------------

//        try (var fout = new FileOutputStream("src/main/resources/numbers.zip");
//             var zout = new ZipOutputStream(fout)) {
//
//            var ze = new ZipEntry("Sherbe4.txt");
//            var ze2 = new ZipEntry("Sherbek3.txt");
//
//            zout.putNextEntry(ze);
//            zout.write("This is a test ".getBytes());
//            zout.write("This is a test ".getBytes());
//            zout.putNextEntry(ze2);
//            zout.setLevel(Deflater.BEST_SPEED);
//
//            zout.write("This is a test ".getBytes());
//            zout.closeEntry();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


        //--------------------------------------------------------------------

//        try (ZipFile zipFile = new ZipFile(new File("src/main/resources/numbers.zip"))) {
//
//            Iterator<? extends ZipEntry> iterator = zipFile.entries().asIterator();
//            while (iterator.hasNext()) {
//                ZipEntry next = iterator.next();
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//    }

    }
}

class ObjectInput_OutputStreamsandSerialization {
    public static void main(String[] args) throws RuntimeException {

//        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/main/resources/users.txt"))) {
//
//            Employee ali = new Employee("Ali", 22);
//            Employee vali = new Employee("Vali", 44);
//
//            objectOutputStream.writeObject(ali);
//            objectOutputStream.writeObject(vali);
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/main/resources/users.txt"))) {
//
//            Employee ali2 = (Employee) objectInputStream.readObject();
//            Employee vali2 = (Employee) objectInputStream.readObject();
//
//
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }


        //--------------------------------------------------------------------------------------


        try ( ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/main/resources/users.txt")) ) {

            Employee asistent = new Employee("asistent", 22, new java.util.Date(1671601440L));
            Manager vali = new Manager("ValiManager", 44, new java.util.Date(1671601440L));
            vali.secretary = asistent;
            Manager ali = new Manager("AliManager", 24, new java.util.Date(1668147071L));
            ali.secretary = asistent;


            objectOutputStream.writeObject(ali);
            objectOutputStream.writeObject(vali);


        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }


        try ( ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/main/resources/users.txt")) ) {
            Date date = new Date(12312312312L);
            Manager ali2 = (Manager) objectInputStream.readObject();
            Manager vali2 = (Manager) objectInputStream.readObject();

            System.out.println(vali2.name);

        } catch ( IOException | ClassNotFoundException e ) {
            throw new RuntimeException(e);
        }
    }

}

class Main {
    //ADVENT OF CODE
    public static int[] arr = new int[3];

    public static void calculate(int num, int index) {
        try {
            if ( arr[index] < num ) {
                int temp = arr[index];
                arr[index] = num;
                calculate(temp, index - 1);
            } else calculate(num, index - 1);
        } catch ( Exception e ) {
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/puzzle.txt"));
        int max = 0;
        int temp = 0;
        String s;
        while ( (s = bufferedReader.readLine()) != null ) {
            if ( s.equals("") ) {
                calculate(temp, 2);
                temp = 0;
            } else {
                temp += Long.parseLong(s);
            }
        }
        System.out.println(arr[0] + arr[1] + arr[2]);

//        List<String> strings = bufferedReader.lines().toList();
//        for (String f : strings) {
//            if (!f.equals("")) {
//                temp = temp + Long.parseLong(f);
//            } else {
//                max = Math.max(max, temp);
//            }
//        }
//11357715
//        .forEach(f -> {
//
//            if (!f.equals("")) {
//                temp.set(temp.get()+Long.parseLong(f));
//            } else {
//                max.set(Math.max(temp.get(), max.get()));
//            }
//        });
        System.out.println(max);
    }
}


class LabeledPoint implements Serializable {
    private String label;
    private transient Point2D.Double point;
}

class SingletonsAndTypesafeEnumerations {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Orientation original = Orientation.HORIZONTAL;
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/main/resources/users.txt"));
        out.writeObject(original);
        out.close();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/resources/users.txt"));
        var saved = (Orientation) in.readObject();
        if ( saved.equals(Orientation.HORIZONTAL) ) {
            System.out.println(saved);
        }
        if ( saved == Orientation.HORIZONTAL ) {
            System.out.println(saved);
        }
    }
}

class Orientation implements Serializable {
    public static final Orientation HORIZONTAL = new Orientation(1);
    public static final Orientation VERTICAL = new Orientation(2);
    private int value;

    private Orientation(int v) {
        value = v;
    }


    /**
     * Agar bu bulmasa tepedagi 2 ta if ham iwlamaydi. Cundi new Object yaratilgan buladi
     * <p>
     * To solve this problem, you need to define another special serialization method,
     * called readResolve. If the readResolve method is defined, it is called after the object
     * is deserialized. It must return an object which then becomes the return value
     * of the readObject method. In our case, the readResolve method will inspect the
     * value field and return the appropriate enumerated constant:
     *
     * @return
     * @throws ObjectStreamException
     */
    protected Object readResolve() throws ObjectStreamException {
        if ( value == 1 ) return Orientation.HORIZONTAL;
        if ( value == 2 ) return Orientation.VERTICAL;
        throw new InvalidObjectException("aasa"); // this shouldn't happen


    }
}

class WorkingWithFiles {

    public static void main(String[] args) throws IOException {
        String baseDir = System.getProperty("user.dir");
        Path basePath = Paths.get(baseDir);
        Path resources = basePath.resolve("resources");
        Path java = resources.resolveSibling("java");
        Path path = Paths.get("/home", "Programming", "CoreJavacolume2", "java");
        System.out.println(path);

//        Files.createDirectory(Path.of("/home/shoxruh/Disk D/Programming/ExampleProjectsHighLevel/CoreJavaVolume2/CoreJavaVolume2/src/main/resources/dir23"));
        Path of = Path.of("/home/shoxruh/Disk D/Programming/ExampleProjectsHighLevel/CoreJavaVolume2/CoreJavaVolume2/src/main/resources/dir/test.txt");
//        Path file = Files.createFile(of);

        boolean exists = Files.exists(of);

        Stream<String> stream = Stream.of("14341234", "12341234123", "4444444444", "2322222222222", "errrrrr");
        Stream<String> stream3 = Stream.of("14341234", "12341234123", "4444444444", "2322222222222", "errrrrr");
        Files.write(Path.of("src/main/resources/dir/test.txt"), stream.toList(), StandardOpenOption.APPEND);

        System.out.println("Files.isReadable(of) = " + Files.isReadable(of));
        System.out.println("Files.isRegularFile(of) = " + Files.isRegularFile(of));
        System.out.println("Files.isDirectory(of) = " + Files.isDirectory(of));
        System.out.println("Files.isExecutable(of) = " + Files.isExecutable(of));
        System.out.println("Files.isHidden(of) = " + Files.isHidden(of));
        System.out.println("Files.isWritable(of) = " + Files.isWritable(of));
        System.out.println("Files.isSymbolicLink(of) = " + Files.isSymbolicLink(of));
        System.out.println("Files.exists(of) = " + Files.exists(of));
        System.out.println("Files.getOwner(of) = " + Files.getOwner(of));

//        BasicFileAttributes attributes = Files.readAttributes(of, BasicFileAttributes.class);
//        System.out.println("attributes.lastAccessTime().toString() = " + attributes.lastAccessTime().toString());
//
//        PosixFileAttributes attributes2 = Files.readAttributes(of, PosixFileAttributes.class);
//        System.out.println("attributes2.group().toString() = " + attributes2.group().toString());
//
//        Stream<Path> list = Files.walk(Path.of("/home/shoxruh/Disk D/Programming/ExampleProjectsHighLevel/CoreJavaVolume2/CoreJavaVolume2/src"));
//        list.forEach(System.out::println);


        DirectoryStream<Path> paths = Files.newDirectoryStream(Path.of("/home/shoxruh/Disk D/Programming/ExampleProjectsHighLevel/CoreJavaVolume2/CoreJavaVolume2/src"));
        for ( Path path1 : paths ) {
            System.out.println(path1);
        }
        try ( DirectoryStream<Path> stream1 = Files.newDirectoryStream(Path.of("/home/shoxruh/Disk D/Programming/ExampleProjectsHighLevel/CoreJavaVolume2/CoreJavaVolume2/src/"), "**.{java,class}"); ) {
            for ( Path path1 : stream1 ) {
                System.out.println(path1);
            }
        }

        Files.walkFileTree(Paths.get("/home/shoxruh/Disk D/Programming/ExampleProjectsHighLevel/CoreJavaVolume2/CoreJavaVolume2/src"), new SimpleFileVisitor<Path>() {
            public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs)
                    throws IOException {
                System.out.println(path);
                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult visitFileFailed(Path path, IOException exc)
                    throws IOException {
                return FileVisitResult.SKIP_SUBTREE;
            }
        });


    }
}

class MemoryMappedFiles {

    public static void main(String[] args) throws IOException {


        try ( RandomAccessFile randomAccessFile = new RandomAccessFile("/home/shoxruh/Disk D/Programming/ExampleProjectsHighLevel/CoreJavaVolume2/CoreJavaVolume2/src/main/resources/dir/test.txt", "rw");
        ) {
            randomAccessFile.writeLong(1);
        } catch ( IOException e ) {
            e.printStackTrace();
        }


        long l = System.currentTimeMillis();
        for ( int i = 0; i < 100_000; i++ ) {
            try ( RandomAccessFile randomAccessFile = new RandomAccessFile("/home/shoxruh/Disk D/Programming/ExampleProjectsHighLevel/CoreJavaVolume2/CoreJavaVolume2/src/main/resources/dir/test.txt", "rw");
            ) {
                FileChannel channel = randomAccessFile.getChannel();
                MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
                long aLong = buffer.getLong(0);
                buffer.putLong(0, i);
            }
        }


        try ( RandomAccessFile randomAccessFile = new RandomAccessFile("/home/shoxruh/Disk D/Programming/ExampleProjectsHighLevel/CoreJavaVolume2/CoreJavaVolume2/src/main/resources/dir/test.txt", "rw");
        ) {
            System.out.println("randomAccessFile.readLong() = " + randomAccessFile.readLong());
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        System.out.println("Time : " + (System.currentTimeMillis() - l)); // 1 sekunddan kam


        long _1 = System.currentTimeMillis();
        for ( int i = 0; i < 100_000; i++ ) {
            try ( RandomAccessFile randomAccessFile = new RandomAccessFile("/home/shoxruh/Disk D/Programming/ExampleProjectsHighLevel/CoreJavaVolume2/CoreJavaVolume2/src/main/resources/dir/test.txt", "rw");
            ) {
                long l1 = randomAccessFile.readLong();
                randomAccessFile.seek(0);
                randomAccessFile.writeLong(i);
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
        try ( RandomAccessFile randomAccessFile = new RandomAccessFile("/home/shoxruh/Disk D/Programming/ExampleProjectsHighLevel/CoreJavaVolume2/CoreJavaVolume2/src/main/resources/dir/test.txt", "rw");
        ) {
            System.out.println("randomAccessFile.readLong() = " + randomAccessFile.readLong());
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        System.out.println("Time : " + (System.currentTimeMillis() - _1));

    }

}

class FileLocking {


    public static void main(String[] args) throws IOException {
        FileChannel channel = FileChannel.open(Path.of("src/main/resources/dir/test.txt"));
        try ( FileLock lock = channel.lock() ) {

        }
//        FileLock lock = channel.lock(0,1000,false);
//        FileLock lock = channel.tryLock();


    }
}

class RegularExpression {

    public static void main(String[] args) {

        Pattern pattern = Pattern.compile("[0-9]{1,2222}");
        Matcher matcher = pattern.matcher("12312");
        List<MatchResult> results = pattern.matcher("2343224rfwf23423432ewr4231423432r324re23423e2e324")
                .results().toList();
        List<String> matches = results.stream()
                .map(MatchResult::group)
                .toList();

        matches.forEach(System.out::println);

    }
}
