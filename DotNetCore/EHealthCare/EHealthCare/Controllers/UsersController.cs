using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Cors;
using EHealthCare.App_Start;
using EHealthCare.Providers;

namespace EHealthCare.Controllers
{

    [EnableCors(origins: "*", headers: "*", methods: "*")]
    public class UsersController : BaseController
    {

        #region User Common Methods


        // POST: api/Users/Login
        [HttpPost]
        public IHttpActionResult Login(User user)
        {
            if (user.IsGoogle.HasValue && user.IsGoogle.Value)
            {
                if (db.Users.ToList().Where(e => e.Email == user.Email).ToList().Count > 0)
                {
                    User u = db.Users.ToList().Where(e => e.Email == user.Email).FirstOrDefault();
                    u.FirstName = user.FirstName;
                    u.LastName = user.LastName;
                    db.SaveChanges();
                }
                else
                {
                    user.IsGoogle = true;
                    db.Users.Add(user);
                    db.SaveChanges();
                }

                return Ok(("Patient", user));
            }

            User loginUser = db.Users.ToList().Where(e => e.UserName == user.UserName || e.Email == user.UserName)
                .Select(e => new User
                {
                    Email = e.Email,
                    FirstName = e.FirstName,
                    Gender = e.Gender,
                    Id = e.Id,
                    IsActive = e.IsActive,
                    LastName = e.LastName,
                    MobileNo = e.MobileNo,
                    Password = e.Password,
                    UserName = e.UserName,
                    UserType = e.UserType,
                    UserImages = e.UserImages.ToList().Where(eImage => eImage.Name.Contains("Profile-" + e.Id)).Select(eImage => new UserImage
                    {
                        Id = eImage.Id,
                        Name = eImage.Name,
                        UserId = eImage.UserId

                    }).ToList()
                }).FirstOrDefault();

            if (loginUser != null)
            {
                if (loginUser.Password == AuthenticationProvider.Encrypt(user.Password))
                {
                    if (loginUser.UserType == Convert.ToByte(EnumProvider.UserType.Admin))
                        return Ok(("Admin", loginUser));

                    else if (loginUser.UserType == Convert.ToByte(EnumProvider.UserType.Hospital))
                        return Ok(("Hospital", loginUser));

                    else if (loginUser.UserType == Convert.ToByte(EnumProvider.UserType.Doctor))
                        return Ok(("Doctor", loginUser));

                    else
                        return Ok(("Patient", loginUser));
                }

                else
                    return Ok(("Password does not match...!!!", new User()));
            }

            return Ok(("User not found...!!!", new User()));
        }


        // POST: api/Users/RegisterHospital
        [HttpPost]
        public IHttpActionResult RegisterHospital(User user)
        {
            try
            {
                user.IsActive = false;
                user.Password = AuthenticationProvider.Encrypt(user.Password);
                user.UserType = Convert.ToByte(EnumProvider.UserType.Hospital);

                db.Users.Add(user);
                db.SaveChanges();

                return Ok("Register Successfully...!!!");
            }
            catch (Exception ex)
            {
                return Ok(InsertUserException(ex));
            }
        }


        // POST: api/Users/RegisterDoctor
        [HttpPost]
        public IHttpActionResult RegisterDoctor(User user)
        {
            try
            {
                user.IsActive = true;
                user.Password = AuthenticationProvider.Encrypt(user.Password);
                user.UserType = Convert.ToByte(EnumProvider.UserType.Doctor);

                db.Users.Add(user);
                db.SaveChanges();

                return Ok("Register Successfully...!!!");
            }
            catch (Exception ex)
            {
                return Ok(InsertUserException(ex));
            }
        }


        // POST: api/Users/RegisterPatient
        [HttpPost]
        public IHttpActionResult RegisterPatient(User user)
        {
            try
            {
                user.IsActive = true;
                user.Password = AuthenticationProvider.Encrypt(user.Password);
                user.UserType = Convert.ToByte(EnumProvider.UserType.Patient);

                db.Users.Add(user);
                db.SaveChanges();

                return Ok("Register Successfully...!!!");
            }
            catch (Exception ex)
            {
                return Ok(InsertUserException(ex));
            }
        }


        private string InsertUserException(Exception ex)
        {
            if (ex.ToString().Contains("UK_Users_Username"))
                return "Username already exists...!!!";

            else if (ex.ToString().Contains("UK_Users_Email"))
                return "Email already exists...!!!";

            else
                return "Please try again...!!!";
        }


        // POST: api/Users/PutActive/5?status=true
        [HttpGet]
        public IHttpActionResult PutActive(int Id, bool status)
        {
            try
            {
                User user = db.Users.Find(Id);
                user.IsActive = status;
                db.SaveChanges();
                return Ok("User Status Changed....!!!");
            }
            catch (Exception ex)
            {
                return Ok("Please try again...!!!");
            }
        }

        [HttpPut]
        public IHttpActionResult PutUpdateUser(User usr)
        {
            try
            {
                User user = db.Users.Find(usr.Id);
                user.LastName = usr.LastName;
                user.FirstName = usr.FirstName;
                user.Gender = usr.Gender;
                user.Email = usr.Email;
                user.MobileNo = usr.MobileNo;
                db.SaveChanges();
                return Ok(("User Detail Changed....!!!", user));
            }
            catch (Exception ex)
            {
                return Ok(("Please try again...!!!", new User()));
            }
        }


        // GET: api/Users/ChangePassword?UserId=5&oldPassword=123456&NewPassword=123456
        [HttpGet]
        public IHttpActionResult ChangePassword(int UserId, string oldPassword, string NewPassword)
        {
            User user = db.Users.Find(UserId);

            if (user.Password == AuthenticationProvider.Encrypt(oldPassword))
            {
                user.Password = AuthenticationProvider.Encrypt(NewPassword);
                db.SaveChanges();

                return Ok("Password change successfully...!!!");
            }
            else return Ok("Old Password does not match...!!!");
        }


        #endregion User Common Methods


        #region Hospital Methods


        // GET: api/Users/GetHospitals
        [HttpGet]
        public IHttpActionResult GetHospitals()
        {
            List<User> users =
                (from hospital in db.Users.ToList()
                 where hospital.UserType == Convert.ToByte(EnumProvider.UserType.Hospital)
                 select new User
                 {
                     Id = hospital.Id,
                     UserType = hospital.UserType,
                     FirstName = hospital.FirstName,
                     LastName = hospital.LastName,
                     Email = hospital.Email,
                     MobileNo = hospital.MobileNo,
                     UserImages = hospital.UserImages.ToList().Where(e => e.Name.Contains("Profile-" + hospital.Id)).Select(e => new UserImage
                     {
                         Id = e.Id,
                         Name = e.Name
                     }).ToList(),

                     UsersAddresses = hospital.UsersAddresses.ToList().Select(e => new UsersAddress
                     {
                         Id = e.Id,
                         Building = e.Building,
                         Street = e.Street,
                         City = new City
                         {
                             Id = e.City.Id,
                             Name = e.City.Name,
                             State = new State
                             {
                                 Id = e.City.State.Id,
                                 Name = e.City.State.Name,
                                 Country = new Country
                                 {
                                     Id = e.City.State.Country.Id,
                                     Name = e.City.State.Country.Name
                                 }
                             }
                         }
                     }).ToList()
                 }).ToList();

            return Ok(users);
        }


        // GET: api/Users/GetHospitalById?HospitalId=5
        [HttpGet]
        public IHttpActionResult GetHospitalById(int HospitalId)
        {
            User user = (from hospital in db.Users.ToList()
                         where hospital.UserType == Convert.ToByte(EnumProvider.UserType.Hospital)
                            && hospital.Id == HospitalId
                         select new User
                         {
                             Id = hospital.Id,
                             UserType = hospital.UserType,
                             FirstName = hospital.FirstName,
                             LastName = hospital.LastName,
                             Email = hospital.Email,
                             MobileNo = hospital.MobileNo,
                             UserImages = hospital.UserImages.ToList().Select(e => new UserImage
                             {
                                 Id = e.Id,
                                 Name = e.Name
                             }).ToList(),

                             UsersAddresses = hospital.UsersAddresses.ToList().Select(e => new UsersAddress
                             {
                                 Id = e.Id,
                                 Building = e.Building,
                                 Street = e.Street,
                                 City = new City
                                 {
                                     Id = e.City.Id,
                                     Name = e.City.Name,
                                     State = new State
                                     {
                                         Id = e.City.State.Id,
                                         Name = e.City.State.Name,
                                         Country = new Country
                                         {
                                             Id = e.City.State.Country.Id,
                                             Name = e.City.State.Country.Name
                                         }
                                     }
                                 }
                             }).ToList()
                         }).FirstOrDefault();

            return Ok(user);
        }


        // GET: api/Users/GetDoctorsByHospital?HospitalId=5&DoctorId=6&SpecializationId=5&Status=true
        [HttpGet]
        public IHttpActionResult GetDoctorsByHospital(int HospitalId, int DoctorId = 0, int SpecializationId = 0, bool? Status = null)
        {
            List<User> users =
                (from doctors in db.Users.ToList()
                 join doctorsSpecialization in db.DoctorSpecializations on doctors.Id equals doctorsSpecialization.DoctorId
                 join hospitalDoctors in db.HospitalDoctors on doctorsSpecialization.Id equals hospitalDoctors.DoctorSpecializationId
                 join specialization in db.Specializations on doctorsSpecialization.SpecializationId equals specialization.Id
                 where doctors.UserType == Convert.ToByte(EnumProvider.UserType.Doctor) && hospitalDoctors.HospitalId == HospitalId
                    && (SpecializationId <= 0 || specialization.Id == SpecializationId)
                    && (DoctorId <= 0 || doctors.Id == DoctorId)
                    && (Status == null || hospitalDoctors.IsActive == Status)
                    && doctors.IsActive == true
                 group doctors by doctors into doctor
                 select new User
                 {
                     Id = doctor.Key.Id,
                     Email = doctor.Key.Email,
                     FirstName = doctor.Key.FirstName,
                     Gender = doctor.Key.Gender,
                     IsActive = doctor.Key.IsActive,
                     LastName = doctor.Key.LastName,
                     MobileNo = doctor.Key.MobileNo,
                     UserName = doctor.Key.UserName,
                     UserType = doctor.Key.UserType,
                     UserImages = doctor.Key.UserImages.Where(e => e.Name.Contains("Profile-" + doctor.Key.Id)).ToList().Select(e => new UserImage
                     {
                         Id = e.Id,
                         Name = e.Name
                     }).ToList(),

                     UsersAddresses = doctor.Key.UsersAddresses.ToList().Select(e => new UsersAddress
                     {
                         Id = e.Id,
                         Building = e.Building,
                         Street = e.Street,
                         City = new City
                         {
                             Id = e.City.Id,
                             Name = e.City.Name,
                             State = new State
                             {
                                 Id = e.City.State.Id,
                                 Name = e.City.State.Name,
                                 Country = new Country
                                 {
                                     Id = e.City.State.Country.Id,
                                     Name = e.City.State.Country.Name
                                 }
                             }
                         }
                     }).ToList()
                 }).ToList();

            return Ok(users);
        }


        // GET: api/Users/GetPatientsByHospital?HospitalId=5&DoctorId=6&PatientId=7
        [HttpGet]
        public IHttpActionResult GetPatientsByHospital(int HospitalId, int DoctorId = 0, int PatientId = 0)
        {
            List<User> users =
                (from patient in db.Users.ToList()
                 join appointment in db.Appointments on patient.Id equals appointment.PatientId
                 join hospital in db.HospitalDoctors on appointment.HospitalDoctorId equals hospital.Id
                 join doctor in db.DoctorSpecializations on hospital.DoctorSpecializationId equals doctor.Id
                 where patient.UserType == Convert.ToByte(EnumProvider.UserType.Patient)
                    && hospital.HospitalId == HospitalId
                    && (PatientId <= 0 || appointment.PatientId == PatientId)
                    && (DoctorId <= 0 || doctor.DoctorId == DoctorId)
                 select patient).ToList();

            return Ok(users);
        }


        #endregion Hospital Methods




        [HttpGet]
        public IHttpActionResult GetDoctors()
        {
            List<User> users =
                (from doctor in db.Users.ToList()
                 where doctor.UserType == Convert.ToByte(EnumProvider.UserType.Doctor)
                 select new User
                 {
                     Id = doctor.Id,
                     Email = doctor.Email,
                     FirstName = doctor.FirstName,
                     Gender = doctor.Gender,
                     IsActive = doctor.IsActive,
                     LastName = doctor.LastName,
                     MobileNo = doctor.MobileNo,
                     UserName = doctor.UserName,
                     UserType = doctor.UserType,

                 }
                     ).ToList();

            return Ok(users);
        }

        [HttpGet]
        public IHttpActionResult GetDoctorsById(int DoctorId)
        {
            User user = (from doctor in db.Users.ToList()
                         where doctor.UserType == Convert.ToByte(EnumProvider.UserType.Doctor)
                            && doctor.Id == DoctorId
                         select new User
                         {
                             Id = doctor.Id,
                             Email = doctor.Email,
                             FirstName = doctor.FirstName,
                             Gender = doctor.Gender,
                             IsActive = doctor.IsActive,
                             LastName = doctor.LastName,
                             MobileNo = doctor.MobileNo,
                             UserName = doctor.UserName,
                             UserType = doctor.UserType,

                         }
                         ).FirstOrDefault();

            return Ok(user);
        }

        [HttpGet]
        public IHttpActionResult GetHospitalsByDoctor(int doctorId)
        {
            byte hospitalUser = Convert.ToByte(EnumProvider.UserType.Hospital);

            List<User> users =
                (from hospitals in db.Users.ToList()
                 join hospitalDoctors in db.HospitalDoctors on hospitals.Id equals hospitalDoctors.HospitalId
                 join doctorsSpecialization in db.DoctorSpecializations on hospitalDoctors.DoctorSpecializationId equals doctorsSpecialization.Id
                 where hospitals.UserType == hospitalUser && doctorsSpecialization.DoctorId == doctorId
                 select new User
                 {
                     Id = hospitalDoctors.Id,
                     Email = hospitals.Email,
                     FirstName = hospitals.FirstName,
                     Gender = hospitals.Gender,
                     IsActive = hospitals.IsActive,
                     LastName = hospitals.LastName,
                     MobileNo = hospitals.MobileNo,
                     UserName = hospitals.UserName,
                     UserType = hospitals.UserType,

                 }).ToList();

            return Ok(users);
        }

        [HttpGet]
        public IHttpActionResult GetPatientsByDoctor(int doctorId)
        {
            byte patientUser = Convert.ToByte(EnumProvider.UserType.Patient);

            List<User> users =
                (from patient in db.Users
                 join appointment in db.Appointments on patient.Id equals appointment.PatientId
                 join hospital in db.HospitalDoctors on appointment.HospitalDoctorId equals hospital.Id
                 join doctorSpecialization in db.DoctorSpecializations on hospital.DoctorSpecializationId equals doctorSpecialization.Id
                 where patient.UserType == patientUser && doctorSpecialization.DoctorId == doctorId
                 select patient).ToList();

            return Ok(users);
        }


        //Patient API's

        [HttpGet]
        public IHttpActionResult GetPatients()
        {
            List<User> users =
                (from paitient in db.Users.ToList()
                 where paitient.UserType == Convert.ToByte(EnumProvider.UserType.Patient)
                 select paitient).ToList();

            return Ok(users);
        }

        [HttpGet]
        public IHttpActionResult PatientDetails(int id)
        {
            User user = (from patient in db.Users.ToList()
                         where patient.UserType == Convert.ToByte(EnumProvider.UserType.Patient) && patient.Id == id
                         select patient).FirstOrDefault();

            return Ok(user);
        }
    }
}