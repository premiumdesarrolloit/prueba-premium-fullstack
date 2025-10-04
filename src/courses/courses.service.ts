import { BadRequestException, Injectable, InternalServerErrorException, Logger, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Course } from './entities/course.entity';
import { DataSource, Repository } from 'typeorm';
import { PaginationDto } from 'src/common/dto/pagination.dto';
import { isUUID } from 'class-validator';
import { CreateCourseDto } from './dto/create-course.dto';
import { UpdateCourseDto } from './dto/update-course.dto';
import { RateCourseDto } from './dto/rate-course.dto';
import { User } from 'src/auth/entities/user.entity';
import { UserCourse } from './entities/user-course.entity';
import { EnrollCourseDto } from './dto/enroll-course.dto';

@Injectable()
export class CoursesService {
    private readonly logger = new Logger('CoursesService');

    constructor(
        @InjectRepository(Course)
        private readonly courseRepository: Repository<Course>,

        @InjectRepository(User)
        private readonly userRepository: Repository<User>,

        @InjectRepository(UserCourse)
        private readonly userCourseRepository: Repository<UserCourse>,

        private readonly dataSource: DataSource,
    ) {}

    async findAll(paginationDto: PaginationDto) {
        const { limit = 10, offset = 0 } = paginationDto;

        const courses = await this.courseRepository.find({
            take: limit,
            skip: offset
        })

        return courses
    }

    async findOne(term: string) {
        let course: Course

        if (isUUID(term)) {
            course = await this.courseRepository.findOneBy({ id: term })
        } else {
            const queryBuilder = this.courseRepository.createQueryBuilder('course_like');

            course = await queryBuilder
                .where('UPPER(nombre) =:nombre or categoria =:categoria', {
                    nombre: term.toUpperCase(),
                    categoria: term.toLowerCase(),
                })
                .getOne();
        }

        if (!course) {
            throw new NotFoundException(`Course with ${term} not found`);
        }

        return course;
    }

    async create(createCourseDto: CreateCourseDto) {
        try {
            const course = this.courseRepository.create({
                ...createCourseDto,
            });

            await this.courseRepository.save( course );

            return course;
        } catch (error) {
            this.handleDBExceptions(error);
        }
    }

    async update( id: string, updateCourseDto: UpdateCourseDto ) {
        const course = await this.courseRepository.preload({ id, ...updateCourseDto });

        if ( !course ) throw new NotFoundException(`Course with id: ${ id } not found`);

        const queryRunner = this.dataSource.createQueryRunner();

        await queryRunner.connect();
        await queryRunner.startTransaction();

        try {
            await queryRunner.manager.save( course );

            await queryRunner.commitTransaction();
            await queryRunner.release();

            return this.findOne( id );
        } catch (error) {
            await queryRunner.rollbackTransaction();
            await queryRunner.release();

            this.handleDBExceptions(error);
        }

    }

    async remove(id: string) {
        const course = await this.findOne( id );

        if (!course) {
            throw new NotFoundException(`Course with id ${id} not found`);
        }

        await this.courseRepository.remove( course );
    }

    async enroll( enrollCourseDto: EnrollCourseDto ) {
        const { userId, courseId } = enrollCourseDto;

        const course = await this.findOne( courseId );

        const user = await this.userRepository.findOneBy({ id: userId })

        if (!course) {
            throw new NotFoundException(`Course with id ${courseId} not found`);
        }

        if (!user) {
            throw new NotFoundException(`User with id ${userId} not found`);
        }

        try {
            const userCourseEnroll = this.userCourseRepository.create({
                ...enrollCourseDto,
            });

            await this.userCourseRepository.save( userCourseEnroll );

            return userCourseEnroll;
        } catch (error) {
            this.handleDBExceptions(error);
        }
    }

    async rate( id: string, rateCourseDto: RateCourseDto ) {
        const course = await this.findOne( id );

        if (!course) {
            throw new NotFoundException(`Course with id ${id} not found`);
        }

        const { userId, rate } = rateCourseDto;

        const courseUser = await this.userCourseRepository.findOneBy({
            userId: userId,
            courseId: id
        });

        if (!courseUser) {
            throw new NotFoundException(`User with id ${userId} not enrolled`);
        }

        if (courseUser.rate && courseUser.rate >= 0) {
            throw new NotFoundException(`User with id ${userId} already rate this course`);
        }

        try {
            courseUser.rate = rate;
    
            this.userCourseRepository.save(courseUser);

            course.evaluacionesTotales += 1;

            const userCourses = await this.userCourseRepository.findBy({
                courseId: id
            });

            const coursesWithRate = userCourses.map((userCourse) => {
                if (!!userCourse.rate && userCourse.rate >= 0) {
                    return userCourse
                }
            })

            let ratingSum = 0;

            coursesWithRate.map((course) => {
                ratingSum += course.rate;
            });

            const puntuacionPromedio = ratingSum / course.evaluacionesTotales;

            course.puntuacionPromedio = puntuacionPromedio;

            const indiceCalidad = (puntuacionPromedio * 10) + (Math.log10(course.inscritos + 1) * 5);

            course.indiceCalidad = indiceCalidad;

            this.courseRepository.save(course);

            return this.findOne(id)
        } catch (error) {
            this.handleDBExceptions(error);
        }

    }

    private handleDBExceptions( error: any ) {
        if ( error.code === '23505' )

        throw new BadRequestException(error.detail);

        this.logger.error(error)

        throw new InternalServerErrorException('Unexpected error, check server logs');
    }
}
