## What technical debt has been cleaned up
We had [logic in our presentation layer](https://code.cs.umanitoba.ca/3350-summer2023/lakers-6/-/blob/main/app/src/main/java/comp3350/lakers/quizme/presentation/SignupActivity.java#L97). [This commit](https://code.cs.umanitoba.ca/3350-summer2023/lakers-6/-/commit/a241be70da4026345bb7c70e4ed2e3667698d391) shows that the logic layer now does the validation. This debt can be classified as reckless/inadvertent because we wanted to ship a feature without thinking about the consequences.

## What technical debt did you leave?
We implemented our UI without good design and planning and it has become super complex and somewhat flaky. While it looks simple, there is plenty of room for improvement. This kind of debt is reckless and deliberate. We learned about good practices in class and we're capable of practicing them but we decided to go "quick and dirty" when we were implementing our UI.

## Discuss a Feature or User Story that was cut/re-prioritized
We decided to make the [achievements](https://code.cs.umanitoba.ca/3350-summer2023/lakers-6/-/issues/8) high priority because we felt that it made more sense for a user to see their achievements, which can motivate them to study harder. It was originally a low priority feature.

## Acceptance test/end-to-end

The [SignupTest](https://code.cs.umanitoba.ca/3350-summer2023/lakers-6/-/blob/01cb29e45f3ff643b0e423ec65424970e3ee0525/app/src/androidTest/java/comp3350/lakers/quizme/SignupTest.java) is a test that involves the UI, logic, and persistence layers. It tests inserting a new user into the database. Initially, [in this commit](https://code.cs.umanitoba.ca/3350-summer2023/lakers-6/-/commit/46b9ec3eb28bee33d73fdfe02e194cf44a293746), the test was super flaky because database kept updating _**after**_ the test. To fix it, a `TestUtil` class and `SystemClock.sleep` were introduced in [this commit](https://code.cs.umanitoba.ca/3350-summer2023/lakers-6/-/commit/badebf57951580a757b83f1cc1f35e3b0ebf3f65).

## Acceptance test, untestable
Our features and UI are simple that it wasn't that hard to make acceptance tests. However, we did run into issues were the database kept updating after the test (see above) and this made it difficult to write tests that are meaningful. Luckily, we were able to fix the flakiness.

## Velocity/teamwork
Our estimates got worse through the course (see the [project velocity]()). In iteration 3, we overestimated some of the features like the [User profile feature](https://code.cs.umanitoba.ca/3350-summer2023/lakers-6/-/issues/44). We estimated that it would take 7 days to do it. In reality, it only took only 3 days to do.