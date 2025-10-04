import { User } from 'src/auth/entities/user.entity';
import { Column, Entity, JoinColumn, ManyToOne, PrimaryColumn } from 'typeorm';
import { Course } from './course.entity';


@Entity('user_course')
export class UserCourse {
    @PrimaryColumn({ name: 'user_id' })
    userId: string;

    @PrimaryColumn({ name: 'course_id' })
    courseId: string;

    @ManyToOne(
        () => User,
        user => user.courses,
        {onDelete: 'NO ACTION', onUpdate: 'NO ACTION'}
    )
    @JoinColumn([{ name: 'user_id', referencedColumnName: 'id' }])
    users: User[];

    @ManyToOne(
        () => Course,
        course => course.users,
        {onDelete: 'NO ACTION', onUpdate: 'NO ACTION'}
    )
    @JoinColumn([{ name: 'course_id', referencedColumnName: 'id' }])
    courses: Course[];

    @Column('decimal', {
        nullable: true
    })
    rate: number;
}
