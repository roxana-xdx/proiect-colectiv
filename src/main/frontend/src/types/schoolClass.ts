/*
    Matchy w/ backend.dto.SchoolClassDTO
    so you use camelCase here huh
 */
export interface SchoolClassDTO {
    classId: number;
    className: string;
    homeroomTeacherId: number | null;
}

/*
    Ditto above, backend.dto.schoolclass.CreateSchoolClassRequest
    IMPORTANT for any lost soul who reads this : 
        somewhere I've heard that the frontend NEEDS to use the same case as the fields in the controllers/DTOs.
        if this proves wrong I will change (notify one of the FrontEnds). so this one would be snake_case.
        never thought I'd see naming cases actually bite me in the ass. please use camelCase
*/
export interface CreateSchoolClassRequest {
    class_name: string;
    homeroom_teacher_id: number;
}