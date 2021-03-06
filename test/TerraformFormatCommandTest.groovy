import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertThat
import static org.hamcrest.Matchers.not
import static org.hamcrest.Matchers.containsString
import static org.hamcrest.Matchers.startsWith

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import de.bechte.junit.runners.context.HierarchicalContextRunner

@RunWith(HierarchicalContextRunner.class)
class TerraformFormatCommandTest {
    @Before
    @After
    public void reset() {
        TerraformFormatCommand.reset()
    }

    public class ToString {
        @Test
        void includesTerraformFormatCommand() {
            def command = new TerraformFormatCommand()

            def actual = command.toString()

            assertThat(actual, startsWith('terraform fmt'))
        }

        public class WithCheck {
            @Test
            void addsCheckOptionByDefault() {
                def command = new TerraformFormatCommand()

                TerraformFormatCommand.withCheck()
                def actual = command.toString()

                assertThat(actual, containsString('-check=true'))
            }

            @Test
            void doesNotAddCheckOptionWhenFalse() {
                def command = new TerraformFormatCommand()

                TerraformFormatCommand.withCheck(false)
                def actual = command.toString()

                assertThat(actual, not(containsString('-check')))
            }

            public class WithPatternOverride {
                @Test
                void usesThePatternWhenCheckIsFalse() {
                    def command = new TerraformFormatCommand()
                    TerraformFormatCommand.withCheck(false)

                    command.withCheckOptionPattern { "valueFor(${it})" }
                    def actual = command.toString()

                    assertThat(actual, containsString('valueFor(false)'))
                }

                @Test
                void usesThePatternWhenCheckIsTrue() {
                    def command = new TerraformFormatCommand()
                    TerraformFormatCommand.withCheck(true)

                    command.withCheckOptionPattern { "valueFor(${it})" }
                    def actual = command.toString()

                    assertThat(actual, containsString('valueFor(true)'))
                }
            }
        }

        public class WithRecursive {
            @Test
            void doesNothingByDefaultUnsupportedByTerraformm11() {
                def command = new TerraformFormatCommand()

                TerraformFormatCommand.withRecursive()
                def actual = command.toString()

                assertEquals(actual, 'terraform fmt')
            }

            public class WithPatternOverride {
                @Test
                void usesThePatternWhenCheckIsFalse() {
                    def command = new TerraformFormatCommand()
                    TerraformFormatCommand.withRecursive(false)

                    command.withRecursiveOptionPattern { "valueFor(${it})" }
                    def actual = command.toString()

                    assertThat(actual, containsString('valueFor(false)'))
                }

                @Test
                void usesThePatternWhenCheckIsTrue() {
                    def command = new TerraformFormatCommand()
                    TerraformFormatCommand.withRecursive(true)

                    command.withRecursiveOptionPattern { "valueFor(${it})" }
                    def actual = command.toString()

                    assertThat(actual, containsString('valueFor(true)'))
                }
            }
        }

        public class WithDiff {
            @Test
            void addsDiffOptionByDefault() {
                def command = new TerraformFormatCommand()

                TerraformFormatCommand.withDiff()
                def actual = command.toString()

                assertThat(actual, containsString('-diff=true'))
            }

            @Test
            void doesNotAddDiffOptionWhenFalse() {
                def command = new TerraformFormatCommand()

                TerraformFormatCommand.withDiff(false)
                def actual = command.toString()

                assertThat(actual, not(containsString('-diff')))
            }

            public class WithPatternOverride {
                @Test
                void usesThePatternWhenCheckIsFalse() {
                    def command = new TerraformFormatCommand()
                    TerraformFormatCommand.withDiff(false)

                    command.withDiffOptionPattern { "valueFor(${it})" }
                    def actual = command.toString()

                    assertThat(actual, containsString('valueFor(false)'))
                }

                @Test
                void usesThePatternWhenCheckIsTrue() {
                    def command = new TerraformFormatCommand()
                    TerraformFormatCommand.withDiff(true)

                    command.withDiffOptionPattern { "valueFor(${it})" }
                    def actual = command.toString()

                    assertThat(actual, containsString('valueFor(true)'))
                }
            }
        }
    }

    public class WithCheck {
        @Test
        void isFluent() {
            def result = TerraformFormatCommand.withCheck()

            assertEquals(TerraformFormatCommand.class, result)
        }
    }

    public class WithRecursive {
        @Test
        void isFluent() {
            def result = TerraformFormatCommand.withRecursive()

            assertEquals(TerraformFormatCommand.class, result)
        }
    }

    public class WithDiff {
        @Test
        void isFluent() {
            def result = TerraformFormatCommand.withDiff()

            assertEquals(TerraformFormatCommand.class, result)
        }
    }

    public class WithCheckOptionPattern {
        @Test
        void isFluent() {
            def command = new TerraformFormatCommand()
            def result = command.withCheckOptionPattern { "somevalue" }

            assertEquals(command, result)
        }
    }

    public class WithRecursiveOptionPattern {
        @Test
        void isFluent() {
            def command = new TerraformFormatCommand()
            def result = command.withRecursiveOptionPattern { 'somevalue' }

            assertEquals(command, result)
        }
    }

    public class WithDiffOptionPattern {
        @Test
        void isFluent() {
            def command = new TerraformFormatCommand()
            def result = command.withDiffOptionPattern { 'somevalue' }

            assertEquals(command, result)
        }
    }
}
