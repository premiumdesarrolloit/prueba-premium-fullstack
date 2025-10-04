import { Body, Controller, Get, Headers, Post, Req, UseGuards } from '@nestjs/common';
import { User } from './entities/user.entity';
import { AuthService } from './auth.service';
import { CreateUserDto, LoginUserDto } from './dto';
import { IncomingHttpHeaders } from 'http';
import { Auth } from './decorators/auth.decorator';
import { AuthGuard } from '@nestjs/passport';
import { RawHeaders } from './decorators/raw-headers.decorator';
import { RoleProtected } from './decorators/role-protected.decorator';
import { ValidRoles } from './interfaces/valid-roles.interface';
import { UserRoleGuard } from './guards/user-role.guard';
import { GetUser } from './decorators/get-user.decorator';

@Controller('auth')
export class AuthController {
    constructor(private readonly authService: AuthService) { }

    @Post('register')
    createUser(@Body() createUserDto: CreateUserDto) {
        return this.authService.create(createUserDto);
    }

    @Post('login')
    loginUser(@Body() loginUserDto: LoginUserDto) {
        return this.authService.login(loginUserDto);
    }

    @Get('check-status')
    @Auth()
    checkAuthStatus(
        @GetUser() user: User
    ) {
        return this.authService.checkAuthStatus(user);
    }
}
