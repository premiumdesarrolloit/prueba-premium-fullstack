import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Course } from './entities/course.entity';
import { ConfigModule } from '@nestjs/config';
import { CoursesService } from './courses.service';
import { CoursesController } from './courses.controller';
import { AuthModule } from 'src/auth/auth.module';
import { UserCourse } from './entities/user-course.entity';

@Module({
    imports: [
        ConfigModule,

        AuthModule,

        TypeOrmModule.forFeature([ Course, UserCourse ]),
    ],
    providers: [CoursesService],
    controllers: [CoursesController]
})
export class CoursesModule {}
