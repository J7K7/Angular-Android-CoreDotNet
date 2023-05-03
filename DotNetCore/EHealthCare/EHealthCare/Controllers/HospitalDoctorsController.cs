using EHealthCare.App_Start;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace EHealthCare.Controllers
{
    public class HospitalDoctorsController : BaseController
    {
        [HttpPost]
        public IHttpActionResult AddDoctorOfHospital(HospitalDoctor hospitalDoctor)
        {
            try
            {
                db.HospitalDoctors.Add(hospitalDoctor);
                db.SaveChanges();
                return Ok("Doctor added with Hospital successfully...!!!");
            }
            catch (Exception ex)
            {
                return Ok("Please try again later...!!!");
            }
        }

        // GET: api/HospitalDoctors/GetByHospitalDoctor?HospitalId=5&DoctorId=6&Status=null
        [HttpGet]
        public IHttpActionResult GetByHospitalDoctor(int HospitalId = 0, int DoctorId = 0, bool? Status = null)
        {
            List<HospitalDoctor> hospitalDoctors =
                (from hospital in db.HospitalDoctors.ToList()
                 where (HospitalId <= 0 || hospital.HospitalId == HospitalId)
                 && (DoctorId <= 0 || hospital.DoctorSpecialization.DoctorId == DoctorId)
                 && (Status == null || hospital.IsActive == Status)
                 select new HospitalDoctor
                 {
                     Id = hospital.Id,
                     HospitalId = hospital.HospitalId,
                     IsActive = hospital.IsActive,
                     Fees = hospital.Fees,
                     DoctorSpecializationId = hospital.DoctorSpecializationId
                 }).ToList();

            foreach (HospitalDoctor doctors in hospitalDoctors)
            {
                doctors.DoctorSpecialization = (
                    from doctor in db.DoctorSpecializations.ToList()
                    join specialization in db.Specializations on doctor.SpecializationId equals specialization.Id
                    where doctors.DoctorSpecializationId == doctor.Id
                    select new DoctorSpecialization
                    {
                        Id = doctor.Id,
                        DoctorId = doctor.DoctorId,
                        SpecializationId = doctor.SpecializationId,
                        Specialization = new Specialization
                        {
                            Id = doctor.Specialization.Id,
                            Name = doctor.Specialization.Name
                        },
                        User = new User
                        {
                            Id = doctor.User.Id,
                            Email = doctor.User.Email,
                            FirstName = doctor.User.FirstName,
                            Gender = doctor.User.Gender,
                            LastName = doctor.User.LastName,
                            MobileNo = doctor.User.MobileNo
                        }
                    }
                    ).FirstOrDefault();
            }

            return Ok(hospitalDoctors);
        }


        // GET: api/HospitalDoctors/PutStatus?Id=5&Status=true
        [HttpGet]
        public IHttpActionResult PutStatus(int Id, bool Status)
        {
            HospitalDoctor doctor = db.HospitalDoctors.Find(Id);
            doctor.IsActive = Status;

            db.SaveChanges();

            return Ok("Updated...!!!");
        }
    }
}
