package br.com.judev.usermanagement.web.validators;

import br.com.judev.usermanagement.exception.CpfCnpj;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {


    private final CPFValidator cpfValidator = new CPFValidator();
    private final CNPJValidator cnpjValidator = new CNPJValidator();

    @Override
    public void initialize(CpfCnpj constraintAnnotation) {
        cpfValidator.initialize(null);
        cnpjValidator.initialize(null);    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Consider using @NotNull or @NotEmpty for null/empty checks
        }

        String cleanedValue = value.replaceAll("[^0-9]", "");

        return isValidCpf(cleanedValue) || isValidCnpj(cleanedValue);
    }

    private boolean isValidCpf(String cpf) {
        // Implement CPF validation logic
        // For example:
        if (cpf.length() != 11) return false;
        // Add CPF validation algorithm here
        return true;
    }

    private boolean isValidCnpj(String cnpj) {
        // Implement CNPJ validation logic
        // For example:
        if (cnpj.length() != 14) return false;
        // Add CNPJ validation algorithm here
        return true;
    }
}
