package org.example;

public class App {
    public static void main(String[] args) {
        String textToEncrypt = "There are two ways of constructing a software design: " +
                "One way is to make it so simple that there are obviously " +
                "no deficiencies, and the other way is to make it so complicated " +
                "that there are no obvious deficiencies. " +
                "The first method is far more difficult";
        String cleanText = textToEncrypt.toUpperCase().replaceAll("[^A-Z]", "");
        System.out.println(cleanText);
        VigenereEncryptor vigenereEncryptor = new VigenereEncryptor();

        String encryptedText = vigenereEncryptor.encrypt(cleanText);
        System.out.println(encryptedText);

        Test test = new Test();
        test.findSubstr(encryptedText, encryptedText.length(), true);
    }
}
