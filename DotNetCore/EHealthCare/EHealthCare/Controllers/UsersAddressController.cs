using EHealthCare.App_Start;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Cors;

namespace EHealthCare.Controllers
{
    [EnableCors(origins: "*", headers: "*", methods: "*")]
    public class UsersAddressController : BaseController
    {
        // GET: api/UsersAddress/GetByUser?UserId=5
        [HttpGet]
        public async Task<IHttpActionResult> GetByUser(int UserId)
        {
            List<UsersAddress> addresses = await db.UsersAddresses.ToListAsync();
            addresses = addresses.Where(e => e.UserId == UserId)
                .Select(e => new UsersAddress
                {
                    Id = e.Id,
                    Building = e.Building,
                    Street = e.Street,
                    CityId = e.CityId,
                    UserId = e.UserId,
                    City = new City
                    {
                        Id = e.City.Id,
                        Name = e.City.Name,
                        StateId = e.City.StateId,
                        State = new State
                        {
                            Id = e.City.State.Id,
                            Name = e.City.State.Name,
                            CountryId = e.City.State.CountryId,
                            Country = new Country
                            {
                                Id = e.City.State.Country.Id,
                                Name = e.City.State.Country.Name
                            }
                        }
                    },
                    User = new User
                    {
                        Id = e.User.Id,
                        Email = e.User.Email,
                        FirstName = e.User.FirstName,
                        Gender = e.User.Gender,
                        IsActive = e.User.IsActive,
                        IsGoogle = e.User.IsGoogle,
                        LastName = e.User.LastName,
                        MobileNo = e.User.MobileNo,
                        UserName = e.User.UserName,
                        UserType = e.User.UserType
                    }
                })
                .ToList();
            return Ok(addresses);
        }


        // GET: api/UsersAddress/GetByUser/5
        [HttpGet]
        public async Task<IHttpActionResult> GetById(int Id)
        {
            UsersAddress address = await db.UsersAddresses.FindAsync(Id);

            if (address != null)
                address = new UsersAddress
                {
                    Id = address.Id,
                    Building = address.Building,
                    Street = address.Street,
                    CityId = address.CityId,
                    UserId = address.UserId,
                    City = new City
                    {
                        Id = address.City.Id,
                        Name = address.City.Name,
                        StateId = address.City.StateId,
                        State = new State
                        {
                            Id = address.City.State.Id,
                            Name = address.City.State.Name,
                            CountryId = address.City.State.CountryId,
                            Country = new Country
                            {
                                Id = address.City.State.Country.Id,
                                Name = address.City.State.Country.Name
                            }
                        }
                    },
                    User = new User
                    {
                        Id = address.User.Id,
                        Email = address.User.Email,
                        FirstName = address.User.FirstName,
                        Gender = address.User.Gender,
                        IsActive = address.User.IsActive,
                        IsGoogle = address.User.IsGoogle,
                        LastName = address.User.LastName,
                        MobileNo = address.User.MobileNo,
                        UserName = address.User.UserName,
                        UserType = address.User.UserType
                    }
                };

            return Ok(address);
        }


        // POST: api/UsersAddress/Post
        [HttpPost]
        public async Task<IHttpActionResult> Post(UsersAddress usersAddress)
        {
            db.UsersAddresses.Add(usersAddress);
            await db.SaveChangesAsync();

            return Ok("Inserted...!!!");
        }


        // POST: api/UsersAddress/Put
        [HttpPost]
        public async Task<IHttpActionResult> Put(UsersAddress usersAddress)
        {
            db.Entry(usersAddress).State = EntityState.Modified;
            await db.SaveChangesAsync();

            return Ok("Updated...!!!");
        }


        // POST: api/UsersAddress/Delete/5
        [HttpPost]
        public async Task<IHttpActionResult> Delete(int Id)
        {
            db.UsersAddresses.Remove(await db.UsersAddresses.FindAsync(Id));
            await db.SaveChangesAsync();

            return Ok("Deleted...!!!");
        }
    }
}