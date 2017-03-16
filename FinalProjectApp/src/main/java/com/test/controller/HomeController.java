package com.test.controller;

import com.ibm.watson.developer_cloud.personality_insights.v2.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v2.model.Profile;
import com.sun.javafx.sg.prism.NGShape;
import com.test.models.ClassesEntity;
import com.test.models.TeacherEntity;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.test.models.StudentEntity;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    String currentUser = "";

    @RequestMapping("/")

    public ModelAndView helloWorld() {
        PersonalityInsights service = new PersonalityInsights();
        service.setUsernameAndPassword("fd5414fb-1232-411b-9edf-40bfc878274c", "aeOAQtv30TuH");
        String text =  "My name is Jeff and I like to code and I like music and etc etc";
               /*

                "Call me Ishmael. Some years ago-never mind how long precisely-having "
                + "little or no money in my purse, and nothing particular to interest me on shore, "
                + "I thought I would sail about a little and see the watery part of the world. "
                + "It is a way I have of driving off the spleen and regulating the circulation. "
                + "Whenever I find myself growing grim about the mouth; whenever it is a damp, "
                + "drizzly November in my soul; whenever I find myself involuntarily pausing before "
                + "coffin warehouses, and bringing up the rear of every funeral I meet; and especially "
                + "whenever my hypos get such an upper hand of me, that it requires a strong moral "
                + "principle to prevent me from deliberately stepping into the street, and methodically "
                + "knocking people's hats off-then, I account it high time to get to sea as soon as I can. "
                + "This is my substitute for pistol and ball. With a philosophical flourish Cato throws himself "
                + "upon his sword; I quietly take to the ship. There is nothing surprising in this. "
                + "If they but knew it, almost all men in their degree, some time or other, cherish "
                + "very nearly the same feelings towards the ocean with me. There now is your insular "
                + "city of the Manhattoes, belted round by wharves as Indian isles by coral reefs-commerce surrounds "
                + "it with her surf. Right and left, the streets take you waterward.";
*/
        String text2 = "Well, thank you very much, Jim, for this opportunity. I want to thank Governor Romney and the University\n" +
                "of Denver for your hospitality.\n" +
                "\n" +
                "There are a lot of points I want to make tonight, but the most important one is that 20 years ago I\n" +
                "became the luckiest man on Earth because Michelle Obama agreed to marry me.\n" +
                "\n" +
                "And so I just want to wish, Sweetie, you happy anniversary and let you know that a year from\n" +
                "now we will not be celebrating it in front of 40 million people.\n" +
                "\n" +
                "You know, four years ago we went through the worst financial crisis since the Great Depression.\n" +
                "Millions of jobs were lost, the auto industry was on the brink of collapse. The financial system\n" +
                "had frozen up.\n" +
                "\n" +
                "And because of the resilience and the determination of the American people, we've begun to\n" +
                "fight our way back. Over the last 30 months, we've seen 5 million jobs in the private sector\n" +
                "created. The auto industry has come roaring back. And housing has begun to rise.\n" +
                "\n" +
                "But we all know that we've still got a lot of work to do. And so the question here tonight is not\n" +
                "where we've been, but where we're going.\n" +
                "\n" +
                "Governor Romney has a perspective that says if we cut taxes, skewed towards the wealthy, and\n" +
                "roll back regulations, that we'll be better off. I've got a different view.\n" +
                "\n" +
                "I think we've got to invest in education and training. I think it's important for us to develop new\n" +
                "sources of energy here in America, that we change our tax code to make sure that we're helping\n" +
                "small businesses and companies that are investing here in the United States, that we take some of\n" +
                "the money that we're saving as we wind down two wars to rebuild America and that we reduce\n" +
                "our deficit in a balanced way that allows us to make these critical investments.\n" +
                "\n" +
                "Now, it ultimately is going to be up to the voters — to you — which path we should take. Are we\n" +
                "going to double on top-down economic policies that helped to get us into this mess or do we\n" +
                "embrace a new economic patriotism that says America does best when the middle class does\n" +
                "best? And I'm looking forward to having that debate.\n" +
                "\n" +
                "Well, let me talk specifically about what I think we need to do. First, we've\n" +
                "got to improve our education system and we've made enormous progress drawing on ideas both\n" +
                "from Democrats and Republicans that are already starting to show gains in some of the toughest\n" +
                "to deal with schools. We've got a program called Race to the Top that has prompted reforms in\n" +
                "46 states around the country, raising standards, improving how we train teachers.\n" +
                "\n" +
                "So now I want to hire another 100,000 new math and science teachers, and create 2 million more\n" +
                "slots in our community colleges so that people can get trained for the jobs that are out there right\n" +
                "now. And I want to make sure that we keep tuition low for our young people.\n" +
                "\n" +
                "When it comes to our tax code, Governor Romney and I both agree that our corporate tax rate is\n" +
                "too high, so I want to lower it, particularly for manufacturing, taking it down to 25 percent. But I\n" +
                "also want to close those loopholes that are giving incentives for companies that are shipping jobs\n" +
                "overseas. I want to provide tax breaks for companies that are investing here in the United States.\n" +
                "\n" +
                "On energy, Governor Romney and I, we both agree that we've got to boost American energy\n" +
                "production, and oil and natural gas production are higher than they've been in years. But I also\n" +
                "\n" +
                "believe that we've got to look at the energy sources of the future, like wind and solar and\n" +
                "biofuels, and make those investments.\n" +
                "\n" +
                "So all of this is possible. Now, in order for us to do it, we do have to close our deficit, and one of\n" +
                "the things I'm sure we'll be discussing tonight is, how do we deal with our tax code? And how do\n" +
                "we make sure that we are reducing spending in a responsible way, but also, how do we have\n" +
                "enough revenue to make those investments?\n" +
                "\n" +
                "And this is where there's a difference, because Governor Romney's central economic plan calls\n" +
                "for a $5 trillion tax cut — on top of the extension of the Bush tax cuts — that's another trillion\n" +
                "dollars — and $2 trillion in additional military spending that the military hasn't asked for. That's\n" +
                "$8 trillion. How we pay for that, reduce the deficit, and make the investments that we need to\n" +
                "make, without dumping those costs onto middle-class Americans, I think is one of the central\n" +
                "questions of this campaign.\n" +
                "\n" +
                "When you add up all the loopholes and deductions that upper-income\n" +
                "individuals can — are currently taking advantage of, you take those all away, you don't come\n" +
                "close to paying for $5 trillion in tax cuts and $2 trillion in additional military spending.\n" +
                "\n" +
                "OBAMA: And that's why independent studies looking at this said the only way to meet\n" +
                "Governor Romney's pledge of not reducing the deficit or -- or — or not adding to the deficit is by\n" +
                "burdening middle-class families. The average middle-class family with children would pay about\n" +
                "$2,000 more.\n" +
                "\n" +
                "Now, that's not my analysis. That's the analysis of economists who have looked at this. And —\n" +
                "and that kind of top — top-down economics, where folks at the top are doing well, so the average\n" +
                "person making $3 million is getting a $250,000 tax break, while middle-class families are\n" +
                "burdened further, that's not what I believe is a recipe for economic growth.\n" +
                "\n" +
                "Well, for 18 months he's been running on this tax plan. And now, five\n" +
                "weeks before the election, he's saying that his big, bold idea is, \"Never mind.\"\n" +
                "\n" +
                "And the fact is that if you are lowering the rates the way you described, Governor, then it is not\n" +
                "possible to come up with enough deductions and loopholes that only affect high-income\n" +
                "individuals to avoid either raising the deficit or burdening the middle class. It's — it's math. It's\n" +
                "arithmetic.\n" +
                "\n" +
                "Now, Governor Romney and I do share a deep interest in encouraging small-business growth. So\n" +
                "at the same time that my tax plan has already lowered taxes for 98 percent of families, I also\n" +
                "lowered taxes for small businesses 18 times. And what I want to do is continue the tax rates —\n" +
                "the tax cuts that we put into place for small businesses and families.\n" +
                "\n" +
                "But I have said that for incomes over $250,000 a year, that we should go back to the rates that we\n" +
                "had when Bill Clinton was president, when we created 23 million new jobs, went from deficit to\n" +
                "surplus, and created a whole lot of millionaires to boot.\n" +
                "\n" +
                "And the reason this is important is because by doing that, we cannot only reduce the deficit, we\n" +
                "cannot only encourage job growth through small businesses, but we're also able to make the\n" +
                "investments that are necessary in education or in energy.\n" +
                "\n" +
                "And we do have a difference, though, when it comes to definitions of small business. Under —\n" +
                "under my plan, 97 percent of small businesses would not see their income taxes go up. Governor\n" +
                "Romney says, well, those top 3 percent, they're the job creators, they'd be burdened.\n" +
                "\n" +
                "But under Governor Romney's definition, there are a whole bunch of millionaires and\n" +
                "billionaires who are small businesses. Donald Trump is a small business. Now, I know Donald\n" +
                "Trump doesn't like to think of himself as small anything, but — but that's how you define small\n" +
                "businesses if you're getting business income.\n" +
                "\n" +
                "And that kind of approach, I believe, will not grow our economy, because the only way to pay\n" +
                "for it without either burdening the middle class or blowing up our deficit is to make drastic cuts\n" +
                "in things like education, making sure that we are continuing to invest in basic science and\n" +
                "research, all the things that are helping America grow. And I think that would be a mistake.\n" +
                "\n" +
                "Jim, I — you may want to move onto another topic, but I — I would just say\n" +
                "this to the American people. If you believe that we can cut taxes by $5 trillion and add $2 trillion\n" +
                "in additional spending that the military is not asking for, $7 trillion — just to give you a sense,\n" +
                "over 10 years, that's more than our entire defense budget — and you think that by closing\n" +
                "loopholes and deductions for the well-to-do, somehow you will not end up picking up the tab,\n" +
                "then Governor Romney's plan may work for you.\n" +
                "\n" +
                "But I think math, common sense, and our history shows us that's not a recipe for job growth.\n" +
                "Look, we've tried this. We've tried both approaches. The approach that Governor Romney's\n" +
                "talking about is the same sales pitch that was made in 2001 and 2003, and we ended up with the\n" +
                "slowest job growth in 50 years, we ended up moving from surplus to deficits, and it all\n" +
                "culminated in the worst financial crisis since the Great Depression.\n" +
                "\n" +
                "Bill Clinton tried the approach that I'm talking about. We created 23 million new jobs. We went\n" +
                "from deficit to surplus. And businesses did very well. So, in some ways, we've got some data on\n" +
                "which approach is more likely to create jobs and opportunity for Americans and I believe that the\n" +
                "economy works best when middle-class families are getting tax breaks so that they've got some\n" +
                "money in their pockets, and those of us who have done extraordinarily well because of this\n" +
                "magnificent country that we live in, that we can afford to do a little bit more to make sure we're\n" +
                "not blowing up the deficit.\n" +
                "\n" +
                "I like it.\n" +
                "\n" +
                "When I walked into the Oval Office, I had more than a trillion-dollar deficit\n" +
                "greeting me. And we know where it came from: two wars that were paid for on a credit card; two\n" +
                "tax cuts that were not paid for; and a whole bunch of programs that were not paid for; and then a\n" +
                "massive economic crisis.\n" +
                "\n" +
                "And despite that, what we've said is, yes, we had to take some initial emergency measures to\n" +
                "make sure we didn't slip into a Great Depression, but what we've also said is, let's make sure that\n" +
                "we are cutting out those things that are not helping us grow.\n" +
                "\n" +
                "So 77 government programs, everything from aircrafts that the Air Force had ordered but weren't\n" +
                "working very well, 1 8 government —18 government programs for education that were well-\n" +
                "intentioned, not weren't helping kids learn, we went after medical fraud in Medicare and\n" +
                "Medicaid very aggressively, more aggressively than ever before, and have saved tens of billions\n" +
                "of dollars, $50 billion of waste taken out of the system.\n" +
                "\n" +
                "And I worked with Democrats and Republicans to cut a trillion dollars out of our discretionary\n" +
                "domestic budget. That's the largest cut in the discretionary domestic budget since Dwight\n" +
                "Eisenhower.\n" +
                "\n" +
                "Now, we all know that we've got to do more. And so I've put forward a specific $4 trillion deficit\n" +
                "reduction plan. It's on a website. You can look at all the numbers, what cuts we make and what\n" +
                "revenue we raise.\n" +
                "\n" +
                "And the way we do it is $2.50 for every cut, we ask for $1 of additional revenue, paid for, as I\n" +
                "indicated earlier, by asking those of us who have done very well in this country to contribute a\n" +
                "little bit more to reduce the deficit. Governor Romney earlier mentioned the Bowles-Simpson\n" +
                "commission. Well, that's how the commission — bipartisan commission that talked about how we\n" +
                "should move forward suggested we have to do it, in a balanced way with some revenue and some\n" +
                "spending cuts. And this is a major difference that Governor Romney and I have.\n" +
                "\n" +
                "Let — let me just finish their point, because you're looking for contrast. You know, when\n" +
                "Governor Romney stood on a stage with other Republican candidates for the nomination and he\n" +
                "was asked, would you take $10 of spending cuts for just $1 of revenue? And he said no.\n" +
                "\n" +
                "Now, if you take such an unbalanced approach, then that means you are going to be gutting our\n" +
                "investments in schools and education. It means that Governor Romney...\n" +
                "\n" +
                "... talked about Medicaid and how we could send it back to the states, but\n" +
                "effectively this means a 30 percent cut in the primary program we help for seniors who are in\n" +
                "nursing homes, for kids who are with disabilities.\n" +
                "\n" +
                "And — and that is not a right strategy for us to move forward.\n" +
                "\n" +
                "Sorry.\n" +
                "\n" +
                "That's what we've done, made some adjustments to it, and we're putting it\n" +
                "forward before Congress right now, a $4 trillion plan...\n" +
                "\n" +
                "Well, we've had this discussion before.\n" +
                "\n" +
                "There has to be revenue in addition to cuts. Now, Governor Romney has\n" +
                "ruled out revenue. He's ruled out revenue.\n" +
                "\n" +
                "If — if we're serious, we've got to take a balanced, responsible approach.\n" +
                "And by the way, this is not just when it comes to individual taxes. Let's talk about corporate\n" +
                "taxes.\n" +
                "\n" +
                "Now, I've identified areas where we can, right away, make a change that I believe would actually\n" +
                "help the economy.\n" +
                "\n" +
                "The oil industry gets $4 billion a year in corporate welfare. Basically, they get deductions that\n" +
                "those small businesses that Governor Romney refers to, they don't get.\n" +
                "\n" +
                "Now, does anybody think that ExxonMobil needs some extra money, when they're making\n" +
                "money every time you go to the pump? Why wouldn't we want to eliminate that? Why wouldn't\n" +
                "we eliminate tax breaks for corporate jets? My attitude is, if you got a corporate jet, you can\n" +
                "probably afford to pay full freight, not get a special break for it.\n" +
                "\n" +
                "When it comes to corporate taxes, Governor Romney has said he wants to, in a revenue neutral\n" +
                "way, close loopholes, deductions — he hasn't identified which ones they are -- but that thereby\n" +
                "bring down the corporate rate.\n" +
                "\n" +
                "Well, I want to do the same thing, but I've actually identified how we can do that. And part of the\n" +
                "way to do it is to not give tax breaks to companies that are shipping jobs overseas.\n" +
                "\n" +
                "Right now, you can actually take a deduction for moving a plant overseas. I think most\n" +
                "Americans would say that doesn't make sense. And all that raises revenue.\n" +
                "\n" +
                "And so if we take a balanced approach, what that then allows us to do is also to help young\n" +
                "people, the way we already have during my administration, make sure that they can afford to go\n" +
                "to college.\n" +
                "\n" +
                "It means that the teacher that I met in Las Vegas, a wonderful young lady, who describes to me —\n" +
                "she's got 42 kids in her class. The first two weeks she's got them, some of them sitting on the\n" +
                "floor until finally they get reassigned. They're using text books that are 10 years old.\n" +
                "\n" +
                "That is not a recipe for growth. That's not how America was built. And so budgets reflect\n" +
                "choices.\n" +
                "\n" +
                "Ultimately, we're going to have to make some decisions. And if we're asking for no revenue, then\n" +
                "that means that we've got to get rid of a whole bunch of stuff.\n" +
                "\n" +
                "And the magnitude of the tax cuts that you're talking about, Governor, would end up resulting in\n" +
                "severe hardship for people, but more importantly, would not help us grow.\n" +
                "\n" +
                "As I indicated before, when you talk about shifting Medicaid to states, we're talking about\n" +
                "potentially a 30 — a 30 percent cut in Medicaid over time.\n" +
                "\n" +
                "Now, you know, that may not seem like a big deal when it just is, you know, numbers on a sheet\n" +
                "of paper, but if we're talking about a family who's got an autistic kid and is depending on that\n" +
                "Medicaid, that's a big problem.\n" +
                "\n" +
                "And governors are creative. There's no doubt about it. But they're not creative enough to make\n" +
                "up for 30 percent of revenue on something like Medicaid. What ends up happening is some\n" +
                "people end up not getting help.\n" +
                "\n" +
                "It's time to end it.\n" +
                "\n" +
                "OK.\n" +
                "\n" +
                "You know, I suspect that, on Social Security, we've got a somewhat similar\n" +
                "position. Social Security is structurally sound. It's going to have to be tweaked the way it was by\n" +
                "Ronald Reagan and Speaker — Democratic Speaker Tip O'Neill. But it is — the basic structure is\n" +
                "sound.\n" +
                "\n" +
                "But — but I want to talk about the values behind Social Security and Medicare, and then talk\n" +
                "about Medicare, because that's the big driver of our deficits right now.\n" +
                "\n" +
                "You know, my grandmother — some of you know — helped to raise me. My grandparents did.\n" +
                "My grandfather died a while back. My grandmother died three days before I was elected\n" +
                "president. And she was fiercely independent. She worked her way up, only had a high school\n" +
                "education, started as a secretary, ended up being the vice president of a local bank. And she\n" +
                "ended up living alone by choice.\n" +
                "\n" +
                "And the reason she could be independent was because of Social Security and Medicare. She had\n" +
                "worked all her life, put in this money, and understood that there was a basic guarantee, a floor\n" +
                "under which she could not go.\n" +
                "\n" +
                "And that's the perspective I bring when I think about what's called entitlements. You know, the\n" +
                "name itself implies some sense of dependency on the part of these folks. These are folks who've\n" +
                "worked hard, like my grandmother, and there are millions of people out there who are counting\n" +
                "on this.\n" +
                "\n" +
                "So my approach is to say, how do we strengthen the system over the long term? And in\n" +
                "Medicare, what we did was we said, we are going to have to bring down the costs if we're going\n" +
                "to deal with our long-term deficits, but to do that, let's look where some of the money's going.\n" +
                "\n" +
                "$716 billion we were able to save from the Medicare program by no longer overpaying insurance\n" +
                "companies by making sure that we weren't overpaying providers. And using that money, we\n" +
                "were actually able to lower prescription drug costs for seniors by an average of $600, and we\n" +
                "were also able to make a -- make a significant dent in providing them the kind of preventive care\n" +
                "that will ultimately save money through the — throughout the system.\n" +
                "\n" +
                "So the way for us to deal with Medicare in particular is to lower health care costs. When it comes\n" +
                "to Social Security, as I said, you don't need a major structural change in order to make sure that\n" +
                "Social Security is there for the future.\n" +
                "\n" +
                "First of all, I think it's important for Governor Romney to present this plan\n" +
                "that he says will only affect folks in the future.\n" +
                "\n" +
                "And the essence of the plan is that you would turn Medicare into a voucher program. It's called\n" +
                "premium support, but it's understood to be a voucher program. His running mate...\n" +
                "\n" +
                "I don't. And let me explain why.\n" +
                "\n" +
                "I understand.\n" +
                "\n" +
                "For — so if you're — if you're 54 or 55, you might want to listen 'cause this -\n" +
                "- this will affect you.\n" +
                "\n" +
                "The idea, which was originally presented by Congressman Ryan, your running mate, is that we\n" +
                "would give a voucher to seniors and they could go out in the private marketplace and buy their\n" +
                "own health insurance.\n" +
                "\n" +
                "The problem is that because the voucher wouldn't necessarily keep up with health care inflation,\n" +
                "it was estimated that this would cost the average senior about $6,000 a year.\n" +
                "\n" +
                "Now, in fairness, what Governor Romney has now said is he'll maintain traditional Medicare\n" +
                "alongside it. But there's still a problem, because what happens is, those insurance companies are\n" +
                "pretty clever at figuring out who are the younger and healthier seniors. They recruit them,\n" +
                "leaving the older, sicker seniors in Medicare. And every health care economist that looks at it\n" +
                "says, over time, what'll happen is the traditional Medicare system will collapse.\n" +
                "\n" +
                "And then what you've got is folks like my grandmother at the mercy of the private insurance\n" +
                "system precisely at the time when they are most in need of decent health care.\n" +
                "\n" +
                "So, I don't think vouchers are the right way to go. And this is not my own — only my opinion.\n" +
                "AARP thinks that the — the savings that we obtained from Medicare bolster the system, lengthen\n" +
                "the Medicare trust fund by eight years. Benefits were not affected at all. And ironically, if you\n" +
                "repeal Obamacare, and I have become fond of this term, \"Obamacare,\" if you repeal it, what\n" +
                "happens is those seniors right away are going to be paying $600 more in prescription care.\n" +
                "They're now going to have to be paying copays for basic checkups that can keep them healthier.\n" +
                "\n" +
                "And the primary beneficiary of that repeal are insurance companies that are estimated to gain\n" +
                "billions of dollars back when they aren't making seniors any healthier. And I don't think that's the\n" +
                "right approach when it comes to making sure that Medicare is stronger over the long term.\n" +
                "\n" +
                "Jim, if I — if I can just respond very quickly, first of all, every study has\n" +
                "shown that Medicare has lower administrative costs than private insurance does, which is why\n" +
                "seniors are generally pretty happy with it.\n" +
                "\n" +
                "And private insurers have to make a profit. Nothing wrong with that. That's what they do. And so\n" +
                "you've got higher administrative costs, plus profit on top of that. And if you are going to save\n" +
                "any money through what Governor Romney's proposing, what has to happen is, is that the money\n" +
                "has to come from somewhere.\n" +
                "\n" +
                "And when you move to a voucher system, you are putting seniors at the mercy of those insurance\n" +
                "companies. And over time, if traditional Medicare has decayed or fallen apart, then they're stuck.\n" +
                "\n" +
                "And this is the reason why AARP has said that your plan would weaken Medicare substantially.\n" +
                "And that's why they were supportive of the approach that we took.\n" +
                "\n" +
                "One last point I want to make. We do have to lower the cost of health care, not just in Medicare\n" +
                "and Medicaid...\n" +
                "\n" +
                "... but - but -- but overall.\n" +
                "\n" +
                "And so...\n" +
                "\n" +
                "Is that a — is that a separate topic?\n" +
                "\n" +
                "I'm sorry.\n" +
                "\n" +
                "Absolutely.\n" +
                "\n" +
                "I think this is a great example. The reason we have been in such a enormous\n" +
                "economic crisis was prompted by reckless behavior across the board.\n" +
                "\n" +
                "Now, it wasn't just on Wall Street. You had loan officers were — that were giving loans and\n" +
                "mortgages that really shouldn't have been given, because the folks didn't qualify. You had people\n" +
                "who were borrowing money to buy a house that they couldn't afford. You had credit agencies\n" +
                "that were stamping these as Al great investments when they weren't.\n" +
                "\n" +
                "But you also had banks making money hand over fist, churning out products that the bankers\n" +
                "themselves didn't even understand, in order to make big profits, but knowing that it made the\n" +
                "entire system vulnerable.\n" +
                "\n" +
                "So what did we do? We stepped in and had the toughest reforms on Wall Street since the 1930s.\n" +
                "We said you've got — banks, you've got to raise your capital requirements. You can't engage in\n" +
                "some of this risky behavior that is putting Main Street at risk. We've going to make sure that\n" +
                "you've got to have a living will so — so we can know how you're going to wind things down if\n" +
                "you make a bad bet so we don't have other taxpayer bailouts.\n" +
                "\n" +
                "In the meantime, by the way, we also made sure that all the help that we provided those banks\n" +
                "was paid back every single dime, with interest.\n" +
                "\n" +
                "Now, Governor Romney has said he wants to repeal Dodd-Frank.\n" +
                "\n" +
                "And, you know, I appreciate and it appears we've got some agreement that a marketplace to work\n" +
                "has to have some regulation. But in the past, Governor Romney has said he just want to repeal\n" +
                "Dodd- Frank, roll it back.\n" +
                "\n" +
                "And so the question is: Does anybody out there think that the big problem we had is that there\n" +
                "was too much oversight and regulation of Wall Street? Because if you do, then Governor\n" +
                "Romney is your candidate. But that's not what I believe.\n" +
                "\n" +
                "Well, four years ago, when I was running for office, I was traveling around\n" +
                "and having those same conversations that Governor Romney talks about. And it wasn't just that\n" +
                "small businesses were seeing costs skyrocket and they couldn't get affordable coverage even if\n" +
                "they wanted to provide it to their employees. It wasn't just that this was the biggest driver of our\n" +
                "federal deficit, our overall health care costs, but it was families who were worried about going\n" +
                "bankrupt if they got sick, millions of families, all across the country.\n" +
                "\n" +
                "If they had a pre-existing condition, they might not be able to get coverage at all. If they did have\n" +
                "coverage, insurance companies might impose an arbitrary limit. And so as a consequence, they're\n" +
                "paying their premiums, somebody gets really sick, lo and behold, they don't have enough money\n" +
                "to pay the bills, because the insurance companies say that they've hit the limit.\n" +
                "\n" +
                "So we did work on this, alongside working on jobs, because this is part of making sure that\n" +
                "middle-class families are secure in this country.\n" +
                "\n" +
                "And let me tell you exactly what Obamacare did. Number one, if you've got health insurance, it\n" +
                "doesn't mean a government takeover. You keep your own insurance. You keep your own doctor.\n" +
                "But it does say insurance companies can't jerk you around. They can't impose arbitrary lifetime\n" +
                "limits. They have to let you keep your kid on their insurance — your insurance plan until you're\n" +
                "26 years old. And it also says that you're going to have to get rebates if insurance companies are\n" +
                "spending more on administrative costs and profits than they are on actual care.\n" +
                "\n" +
                "Number two, if you don't have health insurance, we're essentially setting up a group plan that\n" +
                "allows you to benefit from group rates that are typically 1 8 percent lower than if you're out there\n" +
                "trying to get insurance on the individual market.\n" +
                "\n" +
                "Now, the last point I'd make before...\n" +
                "\n" +
                "No, I think — I had five seconds before you interrupted me, was ...\n" +
                "\n" +
                "... the irony is that we've seen this model work really well in Massachusetts, because Governor\n" +
                "Romney did a good thing, working with Democrats in the state to set up what is essentially the\n" +
                "identical model and as a consequence people are covered there. It hasn't destroyed jobs. And as a\n" +
                "\n" +
                "consequence, we now have a system in which we have the opportunity to start bringing down\n" +
                "costs, as opposed to just leaving millions of people out in the cold.\n" +
                "\n" +
                "Governor Romney said this has to be done on a bipartisan basis. This was a\n" +
                "bipartisan idea. In fact, it was a Republican idea. And Governor Romney at the beginning of this\n" +
                "debate wrote and said what we did in Massachusetts could be a model for the nation.\n" +
                "\n" +
                "And I agree that the Democratic legislators in Massachusetts might have given some advice to\n" +
                "Republicans in Congress about how to cooperate, but the fact of the matter is, we used the same\n" +
                "advisers, and they say it's the same plan.\n" +
                "\n" +
                "It — when Governor Romney talks about this board, for example, unelected board that we've\n" +
                "created, what this is, is a group of health care experts, doctors, et cetera, to figure out, how can\n" +
                "we reduce the cost of care in the system overall?\n" +
                "\n" +
                "Because there — there are two ways of dealing with our health care crisis. One is to simply leave\n" +
                "a whole bunch of people uninsured and let them fend for themselves, to let businesses figure out\n" +
                "how long they can continue to pay premiums until finally they just give up, and their workers are\n" +
                "no longer getting insured, and that's been the trend line.\n" +
                "\n" +
                "Or, alternatively, we can figure out, how do we make the cost of care more effective? And there\n" +
                "are ways of doing it.\n" +
                "\n" +
                "So at Cleveland Clinic, one of the best health care systems in the world, they actually provide\n" +
                "great care cheaper than average. And the reason they do is because they do some smart things.\n" +
                "They — they say, if a patient's coming in, let's get all the doctors together at once, do one test\n" +
                "instead of having the patient run around with 10 tests. Let's make sure that we're providing\n" +
                "preventive care so we're catching the onset of something like diabetes. Let's — let's pay providers\n" +
                "on the basis of performance as opposed to on the basis of how many procedures they've —\n" +
                "they've engaged in.\n" +
                "\n" +
                "Now, so what this board does is basically identifies best practices and says, let's use the\n" +
                "purchasing power of Medicare and Medicaid to help to institutionalize all these good things that\n" +
                "we do.\n" +
                "\n" +
                "And the fact of the matter is that, when Obamacare is fully implemented, we're going to be in a\n" +
                "position to show that costs are going down. And over the last two years, health care premiums\n" +
                "have gone up — it's true — but they've gone up slower than any time in the last 50 years. So we're\n" +
                "already beginning to see progress. In the meantime, folks out there with insurance, you're already\n" +
                "getting a rebate.\n" +
                "\n" +
                "Let me make one last point. Governor Romney says, we should replace it, I'm just going to\n" +
                "repeal it, but — but we can replace it with something. But the problem is, he hasn't described\n" +
                "what exactly we'd replace it with, other than saying we're going to leave it to the states.\n" +
                "\n" +
                "But the fact of the matter is that some of the prescriptions that he's offered, like letting you buy\n" +
                "insurance across state lines, there's no indication that that somehow is going to help somebody\n" +
                "who's got a pre-existing condition be able to finally buy insurance. In fact, it's estimated that by\n" +
                "repealing Obamacare, you're looking at 50 million people losing health insurance...\n" +
                "\n" +
                "... at a time when it's vitally important.\n" +
                "\n" +
                "Let me just point out first of all this board that we're talking about can't\n" +
                "make decisions about what treatments are given. That's explicitly prohibited in the law. But let's\n" +
                "go back to what Governor Romney indicated, that under his plan, he would be able to cover\n" +
                "people with preexisting conditions.\n" +
                "\n" +
                "Well, actually Governor, that isn't what your plan does. What your plan does is to duplicate\n" +
                "what's already the law, which says if you are out of health insurance for three months, then you\n" +
                "can end up getting continuous coverage and an insurance company can't deny you if you've — if\n" +
                "it's been under 90 days.\n" +
                "\n" +
                "But that's already the law and that doesn't help the millions of people out there with preexisting\n" +
                "conditions. There's a reason why Governor Romney set up the plan that he did in Massachusetts.\n" +
                "It wasn't a government takeover of health care. It was the largest expansion of private insurance.\n" +
                "But what it does say is that \"insurers, you've got to take everybody.\"\n" +
                "\n" +
                "Now, that also means that you've got more customers. But when — when Governor Romney says\n" +
                "that he'll replace it with something, but can't detail how it will be in fact replaced and the reason\n" +
                "he set up the system he did in Massachusetts was because there isn't a better way of dealing with\n" +
                "the preexisting conditions problem.\n" +
                "\n" +
                "It just reminds me of, you know, he says that he's going to close deductions and loopholes for his\n" +
                "tax plan. That's how it's going to be paid for, but we don't know the details. He says that he's\n" +
                "going to replace Dodd-Frank, Wall Street reform, but we don't know exactly which ones. He\n" +
                "won't tell us. He now says he's going to replace Obamacare and ensure that all the good things\n" +
                "that are in it are going to be in there and you don't have to worry.\n" +
                "\n" +
                "And at some point, I think the American people have to ask themselves, is the reason that\n" +
                "Governor Romney is keeping all these plans to replace secret because they're too good? Is it — is\n" +
                "it because that somehow middle-class families are going to benefit too much from them?\n" +
                "\n" +
                "No. The reason is, is because, when we reform Wall Street, when we tackle the problem of pre-\n" +
                "existing conditions, then, you know, these are tough problems and we've got to make choices.\n" +
                "And the choices we've made have been ones that ultimately are benefiting middle-class families\n" +
                "all across the country.\n" +
                "\n" +
                "Well, I definitely think there are differences.\n" +
                "\n" +
                "The first role of the federal government is to keep the American people\n" +
                "safe. That's its most basic function. And as commander-in-chief, that is something that I've\n" +
                "worked on and thought about every single day that I've been in the Oval Office.\n" +
                "\n" +
                "But I also believe that government has the capacity, the federal government has the capacity to\n" +
                "help open up opportunity and create ladders of opportunity and to create frameworks where the\n" +
                "American people can succeed.\n" +
                "\n" +
                "Look, the genius of America is the free enterprise system and freedom and the fact that people\n" +
                "can go out there and start a business, work on an idea, make their own decisions.\n" +
                "\n" +
                "But as Abraham Lincoln understood, there are also some things we do better together. So, in the\n" +
                "middle of the Civil War, Abraham Lincoln said, let's help to finance the Transcontinental\n" +
                "Railroad, let's start the National Academy of Sciences, let's start land grant colleges, because we\n" +
                "want to give these gateways of opportunity for all Americans, because if all Americans are\n" +
                "\n" +
                "getting opportunity, we're all going to be better off. That doesn't restrict people's freedom. That\n" +
                "enhances it.\n" +
                "\n" +
                "And so what I've tried to do as president is to apply those same principles.\n" +
                "\n" +
                "And when it comes to education what I've said is we've got to reform schools that are not\n" +
                "working. We use something called Race to the Top. Wasn't a top-down approach, Governor.\n" +
                "What we've said is to states, we'll give you more money if you initiate reforms. And as a\n" +
                "consequence, you had 46 states around the country who have made a real difference.\n" +
                "\n" +
                "But what I've also said is let's hire another 100,000 math and science teachers to make sure we\n" +
                "maintain our technological lead and our people are skilled and able to succeed. And hard-pressed\n" +
                "states right now can't all do that. In fact we've seen layoffs of hundreds of thousands of teachers\n" +
                "over the last several years, and Governor Romney doesn't think we need more teachers. I do,\n" +
                "because I think that that is the kind of investment where the federal government can help.\n" +
                "\n" +
                "It can't do it all, but it can make a difference. And as a consequence we'll have a better trained\n" +
                "workforce and that will create jobs because companies want to locate in places where we've got a\n" +
                "skilled workforce. Well, as I've indicated, I think that it has a significant role to play. Through\n" +
                "our Race to the Top program, we've worked with Republican and Democratic governors to\n" +
                "initiate major reforms, and they're having an impact right now.\n" +
                "\n" +
                "You know, this is where budgets matter, because budgets reflect choices.\n" +
                "So when Governor Romney indicates that he wants to cut taxes and potentially benefit folks like\n" +
                "me and him, and to pay for it we're having to initiate significant cuts in federal support for\n" +
                "education, that makes a difference.\n" +
                "\n" +
                "You know, his — his running mate, Congressman Ryan, put forward a budget that reflects many\n" +
                "of the principles that Governor Romney's talked about. And it wasn't very detailed. This seems to\n" +
                "be a trend. But — but what it did do is to — if you extrapolated how much money we're talking\n" +
                "about, you'd look at cutting the education budget by up to 20 percent.\n" +
                "\n" +
                "When it comes to community colleges, we are seeing great work done out there all over the\n" +
                "country because we have the opportunity to train people for jobs that exist right now. And one of\n" +
                "the things I suspect Governor Romney and I probably agree on is getting businesses to work with\n" +
                "community colleges so that they're setting up their training programs...\n" +
                "\n" +
                "Let me just finish the point.\n" +
                "\n" +
                "The — where they're partnering so that they're designing training programs.\n" +
                "And people who are going through them know that there's a job waiting for them if they\n" +
                "complete it. That makes a big difference, but that requires some federal support.\n" +
                "\n" +
                "Let me just say one final example. When it comes to making college affordable, whether it's two-\n" +
                "year or four-year, one of the things that I did as president was we were sending $60 billion to\n" +
                "banks and lenders as middlemen for the student loan program, even though the loans were\n" +
                "guaranteed. So there was no risk for the banks or the lenders, but they were taking billions out of\n" +
                "the system.\n" +
                "\n" +
                "And we said, \"Why not cut out the middleman?\" And as a consequence, what we've been able to\n" +
                "do is to provide millions more students assistance, lower or keep low interest rates on student\n" +
                "loans. And this is an example of where our priorities make a difference.\n" +
                "\n" +
                "Governor Romney, I genuinely believe cares about education, but when he tells a student that,\n" +
                "you know, \"you should borrow money from your parents to go to college,\" you know, that\n" +
                "indicates the degree to which, you know, there may not be as much of a focus on the fact that\n" +
                "folks like myself, folks like Michelle, kids probably who attend University of Denver, just don't\n" +
                "have that option.\n" +
                "\n" +
                "And for us to be able to make sure that they've got that opportunity and they can walk through\n" +
                "that door, that is vitally important not just to those kids. It's how we're going to grow this\n" +
                "economy over the long term.\n" +
                "\n" +
                "You've done a great job.\n" +
                "\n" +
                "Well, first of all, I think Governor Romney's going to have a busy first day,\n" +
                "because he's also going to repeal Obamacare, which will not be very popular among Democrats\n" +
                "as you're sitting down with them.\n" +
                "\n" +
                "But, look, my philosophy has been, I will take ideas from anybody, Democrat or Republican, as\n" +
                "long as they're advancing the cause of making middle-class families stronger and giving ladders\n" +
                "of opportunity to the middle class. That's how we cut taxes for middle- class families and small\n" +
                "businesses. That's how we cut a trillion dollars of spending that wasn't advancing that cause.\n" +
                "That's how we signed three trade deals into law that are helping us to double our exports and sell\n" +
                "more American products around the world. That's how we repealed \"don't ask/don't tell.\" That's\n" +
                "how we ended the war in Iraq, as I promised, and that's how we're going to wind down the war in\n" +
                "Afghanistan. That's how we went after Al Qaida and bin Laden.\n" +
                "\n" +
                "So we've — we've seen progress even under Republican control of the House of Representatives.\n" +
                "But, ultimately, part of being principled, part of being a leader is, A, being able to describe\n" +
                "\n" +
                "exactly what it is that you intend to do, not just saying, \"I'll sit down,\" but you have to have a\n" +
                "plan.\n" +
                "\n" +
                "Number two, what's important is occasionally you've got to say no, to — to — to folks both in\n" +
                "your own party and in the other party. And, you know, yes, have we had some fights between me\n" +
                "and the Republicans when — when they fought back against us reining in the excesses of Wall\n" +
                "Street? Absolutely, because that was a fight that needed to be had.\n" +
                "\n" +
                "When — when we were fighting about whether or not we were going to make sure that\n" +
                "Americans had more security with their health insurance and they said no, yes, that was a fight\n" +
                "that we needed to have.\n" +
                "\n" +
                "And so part of leadership and governing is both saying what it is that you are for, but\n" +
                "also being willing to say no to some things. And I've got to tell you, Governor Romney, when it\n" +
                "comes to his own party during the course of this campaign, has not displayed that willingness to\n" +
                "say no to some of the more extreme parts of his party.\n" +
                "\n" +
                "Well, Jim, I want to thank you, and I want to thank Governor Romney,\n" +
                "because I think was a terrific debate, and I very much appreciate it. And I want to thank the\n" +
                "University of Denver.\n" +
                "\n" +
                "You know, four years ago, we were going through a major crisis. And yet my faith and\n" +
                "confidence in the American future is undiminished. And the reason is because of its people,\n" +
                "because of the woman I met in North Carolina who decided at 55 to go back to school because\n" +
                "she wanted to inspire her daughter and now has a job from that new training that she's gotten;\n" +
                "because a company in Minnesota who was willing to give up salaries and perks for their\n" +
                "executives to make sure that they didn't lay off workers during a recession.\n" +
                "\n" +
                "The auto workers that you meet in Toledo or Detroit take such pride in building the best cars in\n" +
                "the world, not just because of a paycheck, but because it gives them that sense of pride, that\n" +
                "they're helping to build America. And so the question now is how do we build on those strengths.\n" +
                "And everything that I've tried to do, and everything that I'm now proposing for the next four\n" +
                "years in terms of improving our education system or developing American energy or making\n" +
                "sure that we're closing loopholes for companies that are shipping jobs overseas and focusing on\n" +
                "small businesses and companies that are creating jobs here in the United States, or closing our\n" +
                "deficit in a responsible, balanced way that allows us to invest in our future.\n" +
                "\n" +
                "All those things are designed to make sure that the American people, their genius, their grit, their\n" +
                "determination, is — is channeled and — and they have an opportunity to succeed. And\n" +
                "everybody's getting a fair shot. And everybody's getting a fair share — everybody's doing a fair\n" +
                "share, and everybody's playing by the same rules.\n" +
                "\n" +
                "You know, four years ago, I said that I'm not a perfect man and I wouldn't be a perfect president.\n" +
                "And that's probably a promise that Governor Romney thinks I've kept. But I also promised that\n" +
                "I'd fight every single day on behalf of the American people, the middle class, and all those who\n" +
                "were striving to get into the middle class. I've kept that promise and if you'll vote for me, then I\n" +
                "promise I'll fight just as hard in a second term.\n";

        Profile profile = service.getProfile(text).execute();
        Profile profile2 = service.getProfile(text2).execute();
        System.out.println("---");
        JSONObject json = new JSONObject(profile);
        JSONArray ar = json.getJSONObject("tree").getJSONArray("children");
        JSONObject json2 = new JSONObject(profile2);
        JSONArray ar2 = json2.getJSONObject("tree").getJSONArray("children");

        System.out.println("profile");

        for (int i = 0; i < 2; i++) {
            System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(i).get("percentage"));
            System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(i).get("name"));
            System.out.println(i);
        }


        /*
        System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(0).get("percentage"));
        System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(0).get("name"));
        System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(1).get("percentage"));
        System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(1).get("name"));
        System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(2).get("percentage"));
        System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(2).get("name"));
        System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(3).get("percentage"));
        System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(3).get("name"));
        System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(4).get("percentage"));
        System.out.println(ar.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(4).get("name"));


        System.out.println("--");
        System.out.println(ar2.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(0).get("percentage"));
        System.out.println(ar2.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(0).get("name"));
        System.out.println(ar2.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(1).get("percentage"));
        System.out.println(ar2.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(1).get("name"));
        System.out.println(ar2.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(2).get("percentage"));
        System.out.println(ar2.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(2).get("name"));
        System.out.println(ar2.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(3).get("percentage"));
        System.out.println(ar2.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(3).get("name"));
        System.out.println(ar2.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(4).get("percentage"));
        System.out.println(ar2.getJSONObject(0).getJSONArray("children").getJSONObject(0).getJSONArray("children").getJSONObject(4).get("name"));

        */

        return new
                ModelAndView("welcome", "message", "Welcome! Log In Here.");

    }

    @RequestMapping("welcome")

    public ModelAndView helloWorld2() {
        return new
                ModelAndView("welcome2", "message", "Hello World2");

    }

    @RequestMapping("listStudents")
    public ModelAndView listStudents() {

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

        SessionFactory sessionFact = cfg.buildSessionFactory();

        Session selectStudents = sessionFact.openSession();

        selectStudents.beginTransaction();

        Criteria c = selectStudents.createCriteria(StudentEntity.class);

        ArrayList<StudentEntity> studentList = (ArrayList<StudentEntity>) c.list();

        return new
                ModelAndView("welcome2", "cList", studentList);
    }


@RequestMapping("teacherRegister")
public ModelAndView teacherRegister() {

    String confirm = "";
    return new
            ModelAndView("teacherRegister", "confirmMessage", confirm);
}

    @RequestMapping("studentRegister")
    public ModelAndView studentRegister() {

        String confirm = "";
        return new
                ModelAndView("studentRegister", "confirmMessage", confirm);
    }



    @RequestMapping("addTeacher")
    public ModelAndView addTeacher(@RequestParam("FirstName") String firstName,
                                   @RequestParam("LastName") String lastName,
                                   @RequestParam("userName") String userName,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password){

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = sessionFact.openSession();
        Transaction tx = session.beginTransaction();
        TeacherEntity newTeacher = new TeacherEntity();
        newTeacher.setFirstName(firstName);
        newTeacher.setLastName(lastName);
        newTeacher.setUserName(userName);
        newTeacher.setEmail(email);
        newTeacher.setPassword(password);

        session.save(newTeacher);
        tx.commit();
        session.close();

        String confirm = "Hello " + firstName + "!";

        return new
                ModelAndView("welcome","confirmMessage",confirm);
    }





    @RequestMapping("addStudent")
    public ModelAndView addStudent(@RequestParam("FirstName") String firstName,
                                   @RequestParam("LastName") String lastName,
                                   @RequestParam("userName") String userName,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password){

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session =  sessionFact.openSession();
        Transaction tx = session.beginTransaction();
        StudentEntity newStudent = new StudentEntity();
        newStudent.setFirstName(firstName);
        newStudent.setLastName(lastName);
        newStudent.setUserName(userName);
        newStudent.setEmail(email);
        newStudent.setPassword(password);

        session.save(newStudent);
        tx.commit();
        session.close();

        String confirm = "Hello " + firstName + "!";

        return new
                ModelAndView("welcome","message1",confirm);
    }

    @RequestMapping("valid")
    public ModelAndView testing(@RequestParam("UserName") String userName, @RequestParam("Password") String password) {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session selectStudents = sessionFact.openSession();
        Session selectTeachers = sessionFact.openSession();
        Transaction tx = selectStudents.beginTransaction();
        Transaction tx2 = selectTeachers.beginTransaction();
        StudentEntity loginStudent = (StudentEntity)selectStudents.get(StudentEntity.class, userName);
        TeacherEntity loginTeacher = (TeacherEntity)selectTeachers.get(TeacherEntity.class, userName);

        if (loginStudent== null && loginTeacher != null) {
            Criteria c = selectStudents.createCriteria(StudentEntity.class);

            ArrayList<StudentEntity> studentList = (ArrayList<StudentEntity>) c.list();
            ModelAndView model =  new ModelAndView("teacherPage", "message", loginTeacher.getFirstName() + " TEACHER");
            model.addObject("theList", studentList);
            currentUser = userName;


            return model;
        }
        else if (loginStudent != null && loginTeacher == null) {
            if (loginStudent.getTestResults() == null) {
                return new ModelAndView("loggedIn", "message", loginStudent.getFirstName() + " STUDENT EMPTY TEST");
            }
            else {
                return new ModelAndView("loggedIn", "message", loginStudent.getFirstName() + " STUDENT");
            }
        }
        else {
            return new ModelAndView("welcome", "message1", "Invalid Info!");
        }




    }

    @RequestMapping("createClass")
    public ModelAndView classCreate() throws ClassNotFoundException, SQLException {
        /*
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory session= cfg.buildSessionFactory();
        Session selectClasses = session.openSession();
        Session selectTeachers = session.openSession();
        selectClasses.beginTransaction();
        selectTeachers.beginTransaction();
        Criteria c = selectClasses.createCriteria(ClassesEntity.class);
        Query q = selectClasses.createSQLQuery("SELECT name FROM Classes WHERE teacherUser = (SELECT userName  FROM Teacher INNER JOIN Classes ON Teacher.userName = Classes.teacherUser)");
        List<ClassesEntity> usersList = q.list();


        ArrayList<ClassesEntity> classList = (ArrayList<ClassesEntity>) c.list();
        */


        String url = "jdbc:mysql://finalprojectapp.cl4dqbesxxdh.us-east-2.rds.amazonaws.com/finalprojectapp";
        String userName = "root";
        String password = "admin1212";
        String query = "SELECT * FROM Classes WHERE teacherUser IN (SELECT userName  FROM Teacher INNER JOIN Classes ON Teacher.userName = Classes.teacherUser)";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, userName, password);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<ClassesEntity> classNames = new ArrayList<ClassesEntity>();

        System.out.println(classNames.size());

        while (rs.next()) {
            ClassesEntity temp = new ClassesEntity();
            temp.setName(rs.getString("name"));
            temp.setClassId(rs.getString("classID"));
            classNames.add(temp);
        }
        rs.close();
        st.close();
        con.close();

        return new ModelAndView("createClass", "message", "testing").addObject("theList", classNames);

/*
        ModelAndView model = new ModelAndView("createClass", "message", "testing");
        model.addObject("theList", usersList);


        selectClasses.close();
        selectTeachers.close();
        return model;
        */
    }

    @RequestMapping("classCreated")
    public ModelAndView classCreated(@RequestParam("Classname") String className, @RequestParam("schoolName") String schoolName, @RequestParam("classID") String classID) throws ClassNotFoundException, SQLException {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session session = sessionFact.openSession();
        Transaction tx = session.beginTransaction();
        ClassesEntity checkClass = (ClassesEntity)session.get(ClassesEntity.class, classID);

        if (checkClass == null) {
            ClassesEntity insertClass = new ClassesEntity();
            insertClass.setClassId(classID);
            insertClass.setSchoolName(schoolName);
            insertClass.setName(className);
            insertClass.setTeacherUser(currentUser);
            checkClass = insertClass;
            session.save(checkClass);
            tx.commit();
            session.close();


            String url = "jdbc:mysql://finalprojectapp.cl4dqbesxxdh.us-east-2.rds.amazonaws.com/finalprojectapp";
            String userName = "root";
            String password = "admin1212";
            String query = "SELECT * FROM Classes WHERE teacherUser IN (SELECT userName  FROM Teacher INNER JOIN Classes ON Teacher.userName = Classes.teacherUser)";

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, userName, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            ArrayList<ClassesEntity> classNames = new ArrayList<ClassesEntity>();

            System.out.println(classNames.size());

            while (rs.next()) {
                ClassesEntity temp = new ClassesEntity();
                temp.setName(rs.getString("name"));
                temp.setClassId(rs.getString("classID"));
                classNames.add(temp);
            }
            rs.close();
            st.close();
            con.close();
            return new ModelAndView("createClass", "message", "SUCCESS "  +currentUser).addObject("theList", classNames);
        }
        else {
            return new ModelAndView("createClass", "message", "CLASS EXISTS");

        }




    }
    @RequestMapping("classSelected")
    public ModelAndView editClasses(@RequestParam("selectClass") String classSelected) throws SQLException, ClassNotFoundException {

        String url = "jdbc:mysql://finalprojectapp.cl4dqbesxxdh.us-east-2.rds.amazonaws.com/finalprojectapp";
        String userName = "root";
        String password = "admin1212";
        String query = "SELECT * FROM Classes WHERE teacherUser IN (SELECT userName  FROM Teacher INNER JOIN Classes ON Teacher.userName = Classes.teacherUser)";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, userName, password);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<ClassesEntity> classNames = new ArrayList<ClassesEntity>();

        System.out.println(classNames.size());

        while (rs.next()) {
            ClassesEntity temp = new ClassesEntity();
            temp.setName(rs.getString("name"));
            temp.setClassId(rs.getString("classID"));
            classNames.add(temp);
        }
        rs.close();
        st.close();
        con.close();

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

        SessionFactory sessionFact = cfg.buildSessionFactory();

        Session selectStudents = sessionFact.openSession();

        selectStudents.beginTransaction();

        Criteria c = selectStudents.createCriteria(StudentEntity.class);
         c.add(Restrictions.like("className", classSelected));

        ArrayList<StudentEntity> studentList = new ArrayList<StudentEntity>();
        studentList = (ArrayList<StudentEntity>)c.list();



        return new ModelAndView("createClass", "theList", classNames).addObject("studentList", studentList);
    }
/*
    @RequestMapping("delete") //method for deleting parts of the list
    public ModelAndView deleteCustomer(@RequestParam("id") String id) {
        // temp will store info for the object that we want to delete
        StudentEntity temp = new StudentEntity();
        temp.setCustomerID(id);
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory fact = cfg.buildSessionFactory();
        Session student = fact.openSession();
        student.beginTransaction();
        student.delete(temp);// delete the object from the list
        student.getTransaction().commit();//delete row from the datebase
        ArrayList<CustomersEntity> customerList = getAllCustomers();
        return new
                ModelAndView("welcome2", "cList", customerList);
    }
    */


}
