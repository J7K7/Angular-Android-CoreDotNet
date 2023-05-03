using EHealthCare.App_Start;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Cors;

namespace EHealthCare.Controllers
{
    [EnableCors(origins: "*", headers: "*", methods: "*")]
    public class DoctorSpecializationsController : BaseController
    {
        [HttpPost]
        public IHttpActionResult AddDoctorSpecialization(DoctorSpecialization doctorSpecialization)
        {
            try
            {
                db.DoctorSpecializations.Add(doctorSpecialization);
                db.SaveChanges();
                return Ok("Doctor updated...!!!");
            }
            catch (Exception ex)
            {
                return Ok("Please try again later...!!!");
            }

        }

        [HttpGet]
        public IHttpActionResult ViewDoctorSpecialization(int userId)
        {

            List<DoctorSpecialization> doctorSpecializations = (from doctorspecialization in db.DoctorSpecializations.ToList()
                                                                where doctorspecialization.DoctorId == userId
                                                                select new DoctorSpecialization
                                                                {
                                                                    Id = doctorspecialization.Id,
                                                                    DoctorId = doctorspecialization.DoctorId,
                                                                    Specialization = new Specialization
                                                                    {
                                                                        Id = doctorspecialization.SpecializationId,
                                                                        Name = doctorspecialization.Specialization.Name,
                                                                    }
                                                                    
                                                                }).ToList();
            return Ok(doctorSpecializations);
        }

        [HttpPost]
        public IHttpActionResult UpdateDoctorSpecialization(int id, DoctorSpecialization doctorSpecialization)
        {
            try
            {
                DoctorSpecialization docSpecialization = db.DoctorSpecializations.Find(id);

                docSpecialization = doctorSpecialization;

                db.SaveChanges();

                return Ok("Doctor updated successfully...!!!");
            }
            catch (Exception ex)
            {
                return Ok("Please try again later...!!!");
            }
        }

        [HttpGet]
        public IHttpActionResult DeleteDoctorSpecialization(int id)
        {
            try
            {
                DoctorSpecialization doctorSpecialization = db.DoctorSpecializations.Find(id);
                db.DoctorSpecializations.Remove(doctorSpecialization);
                db.SaveChanges();
                return Ok("Doctor Specialization Removed...!!!");
            }
            catch (Exception ex)
            {
                return Ok("Please try again later...!!!");
            }
        }


        // GET: api/DoctorSpecializations/GetByHospitalDoctorId?HospitalId=5&DoctorId=6&Status=null
        [HttpGet]
        public IHttpActionResult GetByHospitalDoctorId(int HospitalId = 0, int DoctorId = 0, bool? Status = null)
        {
            List<Specialization> specialization =
                (from specializations in db.Specializations.ToList()
                 join doctor in db.DoctorSpecializations on specializations.Id equals doctor.SpecializationId
                 join hospital in db.HospitalDoctors on doctor.Id equals hospital.DoctorSpecializationId
                 where (HospitalId <= 0 || hospital.HospitalId == HospitalId)
                    && (DoctorId <= 0 || doctor.DoctorId == DoctorId)
                    && (Status == null || hospital.IsActive == Status)
                group specializations by specializations into specialized
                 select new Specialization
                 {
                     Id = specialized.Key.Id,
                     Name = specialized.Key.Name
                 }).ToList();

            return Ok(specialization);
        }
    }
}
