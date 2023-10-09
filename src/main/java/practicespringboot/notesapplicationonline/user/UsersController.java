package practicespringboot.notesapplicationonline.user;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import practicespringboot.notesapplicationonline.system.Result;
import practicespringboot.notesapplicationonline.system.StatusCode;
import practicespringboot.notesapplicationonline.user.converter.UsersToUsersDtoConverter;
import practicespringboot.notesapplicationonline.user.dto.UsersDto;

@RestController
@CrossOrigin
@RequestMapping("${api.endpoint.base-url}/users")
public class UsersController {

    private final UsersService usersService;
    private final UsersToUsersDtoConverter usersToUsersDtoConverter;

    public UsersController(UsersService usersService, UsersToUsersDtoConverter usersToUsersDtoConverter) {
        this.usersService = usersService;
        this.usersToUsersDtoConverter = usersToUsersDtoConverter;
    }

    @PostMapping("/register")
    public Result createUser(@Valid @RequestBody Users newUser) {
        Users user = usersService.save(newUser);
        UsersDto userDto = usersToUsersDtoConverter.convert(user);
        return new Result(true, StatusCode.SUCCESS, "Create User Success", userDto);
    }
}
