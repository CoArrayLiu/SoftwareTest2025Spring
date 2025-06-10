```sql
create table Course
(
    course_id            bigint               not null comment 'ID'
        primary key,
    course_name          varchar(255)         null,
    semester             varchar(10)          null,
    year                 int                  null,
    course_average_score float                null,
    quoteIsOpen          tinyint(1) default 0 null
);

create table Experiment
(
    experiment_id   bigint       not null
        primary key,
    experiment_name varchar(50)  null,
    kind            varchar(255) null
);

create table Timer
(
    timer_id    bigint auto_increment
        primary key,
    submit_time datetime null,
    update_time datetime null
);

create table User
(
    user_index bigint auto_increment
        primary key,
    id         varchar(255)                                                         null,
    name       varchar(255)                                                         null,
    password   varchar(255)                                                         null,
    email      varchar(255)                                                         null,
    status     tinyint                                                              null,
    avatar     varchar(255)                                                         null,
    role       enum ('student', 'teacher', 'responsible_teacher', 'system_manager') null,
    constraint user_index
        unique (user_index)
);

create table Student
(
    student_index bigint auto_increment
        primary key,
    school        varchar(255) null,
    course_score  float        null,
    user_index    bigint       null,
    constraint user_index
        unique (user_index),
    constraint Student_ibfk_1
        foreign key (user_index) references User (user_index)
);

create table Report
(
    report_id      bigint auto_increment
        primary key,
    report_content varchar(255)                         null,
    report_comment varchar(1023)                        null,
    report_score   float                                null,
    status         enum ('submitted', 'graded', 'late') null,
    student_index  bigint                               null,
    teacher_index  bigint                               null,
    timer_id       bigint                               null,
    constraint Report_ibfk_3
        foreign key (timer_id) references Timer (timer_id),
    constraint Report_student_index_fk
        foreign key (student_index) references Student (student_index)
);

create index student_index
    on Report (student_index);

create index teacher_index
    on Report (teacher_index);

create index timer_id
    on Report (timer_id);

create table Teacher
(
    teacher_index bigint auto_increment
        primary key,
    department    varchar(255) null,
    user_index    bigint       null,
    constraint user_index
        unique (user_index),
    constraint Teacher_ibfk_1
        foreign key (user_index) references User (user_index)
);

create table experiment_submit
(
    report_id     bigint not null,
    student_index bigint not null,
    experiment_id bigint not null,
    course_id     bigint not null,
    primary key (report_id, student_index, experiment_id, course_id),
    constraint experiment_submit_ibfk_1
        foreign key (report_id) references Report (report_id),
    constraint experiment_submit_ibfk_3
        foreign key (experiment_id) references Experiment (experiment_id),
    constraint experiment_submit_student_index_fk
        foreign key (student_index) references Student (student_index)
);

create index course_id
    on experiment_submit (course_id);

create index experiment_id
    on experiment_submit (experiment_id);

create index student_index
    on experiment_submit (student_index);

create table teacher_assign_experiment
(
    course_id             bigint        not null,
    experiment_id         bigint        not null,
    teacher_index         bigint        not null,
    start_time            date          null,
    end_time              date          null,
    experiment_full_score int           null,
    experiment_book       varchar(255)  null,
    experiment_content    varchar(1023) null,
    report_template       varchar(255)  null,
    primary key (course_id, experiment_id, teacher_index),
    constraint teacher_assign_experiment_ibfk_2
        foreign key (experiment_id) references Experiment (experiment_id),
    constraint teacher_assign_experiment_teacher_index_fk
        foreign key (teacher_index) references Teacher (teacher_index)
);

create index experiment_id
    on teacher_assign_experiment (experiment_id);

create index teacher_index
    on teacher_assign_experiment (teacher_index);

create table teacher_review_student_report
(
    teacher_index bigint not null,
    student_index bigint not null,
    report_id     bigint not null,
    primary key (teacher_index, student_index, report_id),
    constraint teacher_review_student_report_ibfk_3
        foreign key (report_id) references Report (report_id),
    constraint teacher_review_student_report_student_index_fk
        foreign key (student_index) references Student (student_index),
    constraint teacher_review_student_report_teacher_index_fk
        foreign key (teacher_index) references Teacher (teacher_index)
);

create index report_id
    on teacher_review_student_report (report_id);

create index student_index
    on teacher_review_student_report (student_index);

create table teacher_student_course
(
    student_index bigint not null,
    teacher_index bigint not null,
    course_id     bigint not null,
    primary key (student_index, teacher_index, course_id),
    constraint teacher_student_course_cours_id_fk
        foreign key (course_id) references Course (course_id),
    constraint teacher_student_course_student_index_fk
        foreign key (student_index) references Student (student_index),
    constraint teacher_student_course_teacher_index_fk
        foreign key (teacher_index) references Teacher (teacher_index)
);

create index teacher_index
    on teacher_student_course (teacher_index);


```