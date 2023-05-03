using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Cors;
using EHealthCare.App_Start;

namespace EHealthCare.Controllers
{
    [EnableCors(origins: "*", headers: "*", methods: "*")]
    public class SpecializationsController : BaseController
    {
        // GET: api/Specializations/Get
        [HttpGet]
        public async Task<IHttpActionResult> Get()
        {
            List<Specialization> specializations = await db.Specializations.ToListAsync();
            specializations = specializations.Select(e => new Specialization
            {
                Id = e.Id,
                Name = e.Name,
                Time = e.Time
            }).ToList();

            return Ok(specializations);
        }


        // GET: api/Specializations/GetById/5
        [HttpGet]
        public async Task<IHttpActionResult> GetById(int Id)
        {
            Specialization specialization = await db.Specializations.FindAsync(Id);
            return Ok(specialization);
        }


        // POST: api/Specializations/Post
        [HttpPost]
        public async Task<IHttpActionResult> Post(Specialization specialization)
        {
            db.Specializations.Add(specialization);
            await db.SaveChangesAsync();

            return Ok("Inserted...!!!");
        }


        // POST: api/Specializations/Put
        [HttpPost]
        public async Task<IHttpActionResult> Put(Specialization specialization)
        {
            db.Entry(specialization).State = EntityState.Modified;
            await db.SaveChangesAsync();

            return Ok("Updated...!!!");
        }


        // POST: api/Specializations/Delete/5
        [HttpPost]
        public async Task<IHttpActionResult> Delete(int id)
        {
            db.Specializations.Remove(await db.Specializations.FindAsync(id));
            await db.SaveChangesAsync();

            return Ok("Deleted...!!!");
        }
    }
}