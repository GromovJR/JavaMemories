package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroups() {
    List<Object[]> list = new ArrayList<>();
    list.add(new Object[] { new GroupData().withName("GroupName1").withHeader("Header1").withFooter("Footer1")});
    list.add(new Object[] {new GroupData().withName("GroupName2").withHeader("Header2").withFooter("Footer2")});
    list.add(new Object[] {new GroupData().withName("GroupName3").withHeader("Header3").withFooter("Footer3")});
    return list.iterator();
  }

  @Test(dataProvider = "validGroups")
  public void testGroupCreation(GroupData group) throws Exception {
    app.goTo().groupPage();
    Groups before = app.group().all();
    app.group().create(group);

    assertThat(app.group().getCount(), equalTo(before.size() +1));
    Groups after = app.group().all();
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test(enabled = false)
  public void testBadGroupCreation() throws Exception {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("TestName'");
    app.group().create(group);

    assertThat(app.group().getCount(), equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before));
  }

}
