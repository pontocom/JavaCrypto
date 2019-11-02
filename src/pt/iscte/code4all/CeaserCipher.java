package pt.iscte.code4all;

import java.util.Scanner;

public class CeaserCipher {
    private static char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static int find(char c) {
        for(int n=0; n < alphabet.length; n++) {
            if(alphabet[n] == c) {
                return n;
            }
        }
        return 0;
    }

    public static String cipher(String message, int key) {
        char[] umessage = message.toUpperCase().toCharArray();
        char[] ciphertext = new char[umessage.length];

        for(int p = 0; p < umessage.length; p++) {
            if(umessage[p] == ' '){
                ciphertext[p] = ' ';
            } else {
                int pos = find(umessage[p]);
                if(pos + key <= 25) {
                    ciphertext[p] = alphabet[pos+key];
                } else {
                    ciphertext[p] = alphabet[(pos+key)-26];
                }
            }
        }

        return new String(ciphertext);
    }

    public static String decipher(String cipher, int key) {
        char[] ccipher = cipher.toCharArray();
        char[] plaintext = new char[ccipher.length];

        for(int p = 0; p < ccipher.length; p++) {
            if(ccipher[p] == ' '){
                plaintext[p] = ' ';
            } else {
                int pos = find(ccipher[p]);
                if(pos - key >=0 && pos - key <= 25) {
                    plaintext[p] = alphabet[pos-key];
                } else {
                    plaintext[p] = alphabet[(pos-key)+26];
                }
            }
        }

        return new String(plaintext);
    }

    public static void main(String[] args) {
        String message;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your own message: ");
            message = sc.nextLine();

            if(message.compareTo("exit") == 0) System.exit(0);

            System.out.println("Enter key to cipher [1..26]: ");
            int key = sc.nextInt();

            System.out.println(message.toUpperCase());
            String ciphertext = cipher(message, key);
            System.out.println(ciphertext);

            System.out.println("Enter key to decipher [1..26]: ");
            int keyd = sc.nextInt();
            System.out.println(decipher(ciphertext, keyd));
        } while (message.compareTo("exit")!=0);
    }
}
