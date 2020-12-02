import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main {
    public static void main(String[] args) {

        String dir = "C:\\Users\\andre\\Downloads\\OneDrive";
        String target = "C:\\Users\\andre\\Downloads\\Target";
        String sd = "Aufgabe 5";

        try {
            //create target directory if not exists
            File directory = new File(target);
            if (! directory.exists()){
                directory.mkdir();
            }

            //Files.find(Paths.get(dir), 999, (p, bfa) -> bfa.isRegularFile()).forEach(System.out::println);
            Stream<Path> r = Files.find(Paths.get(dir), 999, (p, bfa) -> {
                return bfa.isDirectory();
            });

            for (Path p : (Iterable<Path>) () -> r.iterator()) {
                if(p.getFileName().toString().contains(sd)) {
                    Path parent = p.getParent();
                    System.out.println(parent.toString());
                    try {
                        String nt = target + "\\"  + p.getParent().getFileName().toString();
                        copyFolder(p, Paths.get(nt));
                    } catch (Exception ex) {
                        System.out.println("Copy failed!");
                    }
                }
            }
        }
        catch(Exception ex)
        {
            System.out.println("IO Exception!");
        }

        System.out.println("The end!");
    }

    public static void copyFolder(Path src, Path dest) throws IOException {
        try (Stream<Path> stream = Files.walk(src)) {
            stream.forEach(source -> copy(source, dest.resolve(src.relativize(source))));
        }
    }

    private static void copy(Path source, Path dest) {
        try {
            Files.copy(source, dest, REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
