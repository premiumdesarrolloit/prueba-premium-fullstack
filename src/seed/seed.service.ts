import { Injectable } from '@nestjs/common';

import { seedData } from './data/seed-data'
import { User } from 'src/auth/entities/user.entity';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Course } from 'src/courses/entities/course.entity';

@Injectable()
export class SeedService {
    constructor(
        @InjectRepository( User )
        private readonly userRepository: Repository<User>,
        @InjectRepository( Course )
        private readonly courseRepository: Repository<Course>
    ) {}

    async runSeed() {
        await this.insertUsers();
        await this.insertCourses();

        return 'SEED EXECUTED';
    }

    private async insertUsers() {
        const seedUsers = seedData.users;
        
        const users: User[] = [];

        seedUsers.forEach( user => {
            users.push( this.userRepository.create( user ) )
        });

        const dbUsers = await this.userRepository.save( seedUsers )

        return dbUsers[0];
    }

    private async insertCourses() {
        const seedCourses = seedData.courses;
        
        const courses: Course[] = [];

        seedCourses.forEach( user => {
            courses.push( this.courseRepository.create( user ) )
        });

        const dbUsers = await this.courseRepository.save( seedCourses )

        return dbUsers[0];
    }
}
