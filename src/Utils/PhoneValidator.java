package Utils;

public class PhoneValidator {

    // Código de país (1-3 dígitos)
    public static boolean isCountryCodeValid(int code) {
        return code >= 0 && code <= 999;
    }

    // Número de teléfono (1-11 dígitos)
    public static boolean isPhoneNumberValid(long number) {
        return number >= 0 && String.valueOf(number).length() <= 11;
    }
}