import { BadRequestException, Injectable, InternalServerErrorException, Logger, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Course } from './entities/course.entity';
import { DataSource, Repository } from 'typeorm';
import { PaginationDto } from 'src/common/dto/pagination.dto';
import { isUUID } from 'class-validator';
import { CreateCourseDto } from './dto/create-course.dto';
import { UpdateCourseDto } from './dto/update-course.dto';

@Injectable()
export class CoursesService {
    private readonly logger = new Logger('CoursesService');

    constructor(
        @InjectRepository(Course)
        private readonly courseRepository: Repository<Course>,

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

    private handleDBExceptions( error: any ) {
        if ( error.code === '23505' )

        throw new BadRequestException(error.detail);

        this.logger.error(error)

        throw new InternalServerErrorException('Unexpected error, check server logs');
    }
}
