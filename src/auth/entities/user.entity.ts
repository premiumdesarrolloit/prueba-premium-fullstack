import { Course } from 'src/courses/entities/course.entity';
import { BeforeInsert, BeforeUpdate, Column, Entity, JoinTable, ManyToMany, PrimaryGeneratedColumn } from 'typeorm';


@Entity('users')
export class User {
    
    @PrimaryGeneratedColumn('uuid')
    id: string;

    @Column('text', {
        unique: true
    })
    email: string;

    @Column('text', {
        select: false
    })
    password: string;

    @Column('text')
    fullName: string;

    @Column('bool', {
        default: true
    })
    isActive: boolean;

    @Column('text', {
        array: true,
        default: ['user']
    })
    roles: string[];

    @ManyToMany(
        () => Course,
        (course) => course.users,
        { onDelete: 'NO ACTION', onUpdate: 'NO ACTION' }
    )
    @JoinTable({
        name: 'user_course',
        joinColumn: {
            name: 'user_id',
            referencedColumnName: 'id',
        },
        inverseJoinColumn: {
            name: 'course_id',
            referencedColumnName: 'id',
        },
    })
    courses: Course[]

    @BeforeInsert()
    checkFieldsBeforeInsert() {
        this.email = this.email.toLowerCase().trim();
    }

    @BeforeUpdate()
    checkFieldsBeforeUpdate() {
        this.checkFieldsBeforeInsert();
    }

}
