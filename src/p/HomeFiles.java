package p;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
public class HomeFiles {
    static List<String> read(File file) throws IOException {
        ArrayList<String> lines=new ArrayList<>();
        Reader reader=new FileReader(file);
        String line="";
        TreeSet<Character> characters=new TreeSet<>();
        for(int c=reader.read();c!=-1;c=reader.read()) {
            switch(c) {
                case '\r':
                    break;
                case '\n':
                    lines.add(line);
                    line="";
                    break;
                default:
                    if(!!Character.isLetterOrDigit(c));
                    else if(!!Character.isSpace((char)c));
                    else if(!!Character.isISOControl((char)c));
                    else if(33<=c&&c<=47);
                    else if(58<=c&&c<=64);
                    else if(123<=c&&c<=126);
                    else if(c==65533) {
                        System.out.println("<<<");
                        System.out.println(line);
                        System.out.println("got: "+c);
                        System.out.println(line);
                        System.out.println(">>>");
                    } else {
                        //System.out.println(c+" is unusual");
                        characters.add(Character.valueOf((char)c));
                    }
                    line+=(char)c;
                    break;
            }
        }
        if(!line.equals("")) lines.add(line);
        System.out.println("special: "+characters);
        return lines;
    }
    static void process(File file) throws IOException {
        List<String> lines=read(file);
        System.out.println(file+" "+lines);
        if(true) return;
        Path path=Path.of(file.toString());
        System.out.println(path);
        //List<String> lines=Files.readAllLines(Paths.get(path),Charset.defaultCharset());
        List<String> lines2=Files.readAllLines(Paths.get(file.toString()),StandardCharsets.UTF_8);
        System.out.println(lines2);
    }
    public static void main(String[] args) throws IOException {
        File dir=new File("D:\\ray\\files");
        List<String> names=Arrays.asList(dir.list());
        TreeSet<String> set=new TreeSet<>(names);
        List<File> files=new ArrayList<>();
        for(String name:set) {
            File file=new File(dir,name);
            if(file.exists()&&file.isFile()) if(name.startsWith("phon")&&name.endsWith(".txt")) files.add(file);
        }
        System.out.println(files);
        for(File file:files) {
            process(file);
            //break;
        }
    }
}
