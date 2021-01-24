package com.albanj.capitalize.capitalizeback.validator.File;

import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class FileValidator implements ConstraintValidator<FileDtoValidation, FileDto> {

    private final FileRepository repo;

    @Autowired
    public FileValidator(FileRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean isValid(FileDto fileDto, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("DANS LE VALIDATOR FILE");
        if (fileDto == null) return false;
        if (fileDto.getId() != null) {
            if(repo.findById(fileDto.getId()).isEmpty()) return false;
        }
        if (fileDto.getPath().contains("..")) {
            return false;
        }
        try {
            Paths.get(fileDto.getPath());

        }catch (InvalidPathException e) {
            return false;
        }
        return true;
    }
}
