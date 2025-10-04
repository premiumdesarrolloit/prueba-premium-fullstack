import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ConfigModule } from '@nestjs/config';
import appConfig from './config/app.config';
import dbConfig from './config/db.config';
import authConfig from './config/auth.config';
import { envValidationSchema } from './config/env.validation';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AuthModule } from './auth/auth.module';
import { CoursesModule } from './courses/courses.module';
import { CommonModule } from './common/common.module';

function envFiles() {
  const env = process.env.NODE_ENV ?? 'development';
  return [`.env.${env}`, '.env'];
}

@Module({
  imports: [
    // ENV FILES LOAD & VALIDATION
    ConfigModule.forRoot({
      envFilePath: envFiles(),
      isGlobal: true,
      load: [
        appConfig,
        authConfig,
        dbConfig
      ],
      validationSchema: envValidationSchema,
      validationOptions: {
        allowUnknown: true,
        abortEarly: true,
      },
      expandVariables: true,
      cache: true,
    }),
    ConfigModule.forRoot(),

    // POSTGRESQL DB CONNECTION
    TypeOrmModule.forRoot({
      type: 'postgres',
      host: process.env.DB_HOST,
      port: +(process.env.DB_PORT || 5432),
      database: process.env.DB_NAME,
      username: process.env.DB_USER,
      password: process.env.DB_PASS,
      autoLoadEntities: true,
      synchronize: true,
    }),

    AuthModule,

    CoursesModule,

    CommonModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
