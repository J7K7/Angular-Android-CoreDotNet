using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Http.Cors;
using EHealthCare.App_Start;

namespace EHealthCare.Controllers
{
    [EnableCors(origins: "*", headers: "*", methods: "*")]
    public class UserImagesController : BaseController
    {
        // GET: api/UserImages/GetByUserId?UserId=5
        [HttpGet]
        public IHttpActionResult GetByUserId(int UserId)
        {
            List<UserImage> images = db.UserImages.ToList().Where(e => e.UserId == UserId && !e.Name.Contains("Profile"))
                .Select(e => new UserImage
                {
                    Id = e.Id,
                    Name = e.Name,
                    UserId = e.UserId
                }).ToList();

            return Ok(images);
        }


        // POST: api/UserImages/PostProfileImage?UserId=5
        [HttpPost]
        public IHttpActionResult PostProfileImage(int UserId)
        {
            UserImage image = db.UserImages.ToList().Where(e => e.Name.Contains("Profile") && e.UserId == UserId).FirstOrDefault();

            HttpPostedFile file = HttpContext.Current.Request.Files[0];

            if (image != null && image.Id != 0)
            {
                string FileName = HttpContext.Current.Server.MapPath("~/Content/Images/") + image.Name;

                if (File.Exists(FileName))
                    File.Delete(FileName);
            }
            else image = new UserImage();

            image.UserId = UserId;

            image.Name = "Profile-" + UserId + "." + file.FileName.Split('.')[file.FileName.Split('.').Length - 1];

            file.SaveAs(HttpContext.Current.Server.MapPath("~/Content/Images/") + image.Name);

            if (image.Id == 0)
                db.UserImages.Add(image);

            db.SaveChanges();

            return Ok(("Updated...!!!", image.Name));
        }


        // POST: api/UserImages/Post?UserId=5
        [HttpPost]
        public IHttpActionResult Post(int UserId)
        {
            UserImage image = new UserImage();

            HttpPostedFile file = HttpContext.Current.Request.Files[0];

            image.UserId = UserId;
            image.Name = DateTime.Now.Ticks.ToString() + "." + file.FileName.Split('.')[file.FileName.Split('.').Length - 1];

            file.SaveAs(HttpContext.Current.Server.MapPath("~/Content/Images/") + image.Name);

            db.UserImages.Add(image);
            db.SaveChanges();

            return Ok("Inserted...!!!");
        }


        // GET: api/UserImages/Delete/5
        [HttpGet]
        public IHttpActionResult Delete(int Id)
        {
            UserImage image = db.UserImages.ToList().Where(e => e.Id == Id).FirstOrDefault();
            string Path = HttpContext.Current.Server.MapPath("~/Content/Images/");

            if (!string.IsNullOrEmpty(image.Name))
                if (File.Exists(Path + image.Name))
                    File.Delete(Path + image.Name);

            db.UserImages.Remove(image);
            db.SaveChanges();

            return Ok("Deleted...!!!");
        }
    }
}