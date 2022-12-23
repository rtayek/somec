package p;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
public class Phone {
    static int toInt(byte b) { return b>=0?b:(256+b); }
    public static void main(String[] args) throws IOException {
        System.out.println(path);
        String[] names=path.list();
        TreeSet<File> files=new TreeSet<>();
        for(String name:names) {
            if(name.endsWith(".txt")) { System.out.println(name); files.add(new File(path,name)); }
        }
        System.out.println(files);
        TreeMap<File,byte[]> map=new TreeMap<>();
        for(File file:files) {
            byte[] bytes=Files.readAllBytes(file.toPath());
            map.put(file,bytes);
        }
        TreeSet<Character> all=new TreeSet<>();
        TreeSet<File> nonAscii=new TreeSet<>();
        for(File key:map.keySet()) {
            boolean ok=true;
            TreeSet<Character> characters=new TreeSet<>();
            byte[] bytes=map.get(key);
            String string=new String(bytes);
            for(int i=0;i<string.length();++i) if(string.charAt(i)>=128) {
                //System.out.println("non ascii char: "+string.charAt(i)+" at: "+i);
                characters.add(Character.valueOf((string.charAt(i))));
            }
            for(int i=0;i<bytes.length;++i) if(Byte.toUnsignedInt(bytes[i])>=128) {
                //System.out.println("non ascii byte: "+bytes[i]+" at position: "+i);
                characters.add(Character.valueOf((char)toInt(bytes[i])));
            }
            if(characters.size()>0) {
                nonAscii.add(key);
                System.out.print(key+" "+characters.size());
                for(Character c:characters) System.out.print(" "+Integer.toString(c,16)+" "+(int)c+" '"+c+"'");
                System.out.println();
            }
            if(characters.size()>0) System.out.println("added "+characters.size());
            all.addAll(characters);
            // split the bytes into lines
            ArrayList<String> lines=new ArrayList<>();
            ArrayList<Byte> line=new ArrayList<>();
            for(byte b:bytes) {
                if(b=='\n') { //
                    lines.add(String.valueOf(line.toArray()));
                    line.clear();
                } else {
                    if(toInt(b)>=128) {
                        ok=false;
                        System.out
                        .println("line: "+lines.size()+", poistion: "+line.size()+", strange: "+b+" "+toInt(b));
                    }
                    line.add(b);
                }
            }
            lines.add(String.valueOf(line.toArray()));
            if(!ok) System.out.println("end of key: "+key);
        }
    }
    static final File path=new File("D:\\ray\\files");
}
