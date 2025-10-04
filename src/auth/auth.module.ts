import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { User } from './entities/user.entity';
import { PassportModule } from '@nestjs/passport';
import { JwtModule } from '@nestjs/jwt';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { JwtStrategy } from './stratergy/jwt.stratergy';
import { AuthService } from './auth.service';
import { AuthController } from './auth.controller';

@Module({
    imports: [
        ConfigModule,

        TypeOrmModule.forFeature([ User ]),

        PassportModule.register({ defaultStrategy: 'jwt' }),

        JwtModule.registerAsync({
            imports: [ConfigModule],
            inject: [ConfigService],
            useFactory: (configService: ConfigService) => {
                return {
                    secret: configService.get('JWT_SECRET'),
                    signOptions: {
                        expiresIn: configService.get('JWT_EXPIRES_IN')
                    }
                }
            }
        })
    ],
    providers: [
        JwtStrategy,
        AuthService
    ],
    exports: [
        TypeOrmModule,
        JwtStrategy,
        PassportModule,
        JwtModule
    ],
    controllers: [AuthController]
})
export class AuthModule {}
