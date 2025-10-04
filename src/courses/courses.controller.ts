import { Body, Controller, Delete, Get, Param, ParseUUIDPipe, Post, Put, Query } from '@nestjs/common';
import { CoursesService } from './courses.service';
import { PaginationDto } from 'src/common/dto/pagination.dto';
import { Auth } from 'src/auth/decorators/auth.decorator';
import { ValidRoles } from 'src/auth/interfaces/valid-roles.interface';
import { CreateCourseDto } from './dto/create-course.dto';
import { UpdateCourseDto } from './dto/update-course.dto';
import { RateCourseDto } from './dto/rate-course.dto';
import { EnrollCourseDto } from './dto/enroll-course.dto';

@Controller('courses')
export class CoursesController {
    constructor(private readonly coursesService: CoursesService) {}

    @Get()
    @Auth( ValidRoles.user )
    findAll( @Query() paginationDto: PaginationDto ) {
        return this.coursesService.findAll( paginationDto );
    }

    @Get(':term')
    findOne( @Param( 'term' ) term: string ) {
        return this.coursesService.findOne( term );
    }

    @Post()
    @Auth( ValidRoles.admin )
    create(
        @Body() createCourseDto: CreateCourseDto,
    ) {
        return this.coursesService.create( createCourseDto );
    }

    @Put(':id')
    @Auth( ValidRoles.admin )
    update(
        @Param('id', ParseUUIDPipe ) id: string, 
        @Body() updateCourseDto: UpdateCourseDto
    ) {
        return this.coursesService.update( id, updateCourseDto );
    }

    @Delete(':id')
    @Auth( ValidRoles.admin )
    remove(@Param('id', ParseUUIDPipe ) id: string) {
        return this.coursesService.remove( id );
    }

    @Post()
    @Auth()
    enrollCourse(
        @Body() enrollCourseDto: EnrollCourseDto,
    ) {
        return this.coursesService.enroll( enrollCourseDto );
    }

    @Post()
    @Auth()
    rateCourse(
        @Param('id', ParseUUIDPipe ) id: string,
        @Body() rateCourseDto: RateCourseDto,
    ) {
        return this.coursesService.rate( id, rateCourseDto );
    }
}
