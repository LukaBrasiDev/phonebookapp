select *
from contacts where fk_category = (select id from categories where title = "home");

select * from contacts
join contact_tag on contact_id = contact_tag.contact_id
join tags on contact_tag.tag_id = tags.id
where tags.id=(select id from tags where title="famiglia");