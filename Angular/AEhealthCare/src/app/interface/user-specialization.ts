import { Specialization } from "./specialization";

export interface UserSpecialization {
    Id?: number;
    DoctorId?: number;
    SpecializationId?: number;
    specialization?: Specialization;
}
