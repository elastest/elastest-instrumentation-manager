[![License badge](https://img.shields.io/badge/license-Apache2-green.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Documentation badge](https://img.shields.io/badge/docs-latest-brightgreen.svg)](http://elastest.io/docs/)
[![Build Status](https://ci.elastest.io/jenkins/buildStatus/icon?job=elastest-instrumentation-manager/eim)](https://ci.elastest.io/jenkins/job/elastest-instrumentation-manager/eim)
[![codecov](https://codecov.io/gh/elastest/elastest-instrumentation-manager/branch/master/graph/badge.svg)](https://codecov.io/gh/elastest/elastest-instrumentation-manager)

[![][ElasTest Logo]][ElasTest]

Copyright © 2017-2019 [Atos Spain]. Licensed under [Apache 2.0 License].

elastest-instrumentation-manager (eim)
==============================
The ElasTest Instrumentation Manager (EIM) component controls and orchestrates the Instrumentation Agents that are deployed in [ElasTest] platform. These agents will instrument the operating system kernel of the SuT (Software under test) host instances. Thanks to it, the agent will be capable of exposing two types of capabilities: 
1) Controllability, through which the agent can force custom behaviours on the host’s network, CPU utilization, memory consumption, process lifecycle management or system shutdown, etc.
2) Observability, through which the Agent collects all information relevant for testing or monitoring purposes (e.g. energy consumption, resources utilization, etc.)

The EIM component is currently in development. If you want to contribute to the EIM project you need to read the [Development documentation](https://github.com/elastest/elastest-instrumentation-manager/blob/master/docs/index.md) 

# What is ElasTest

This repository is part of [ElasTest], which is an open source elastic platform
aimed to simplify end-to-end testing. ElasTest platform is based on three
principles 
<br>
i) Test orchestration Combining intelligently testing units for
creating a more complete test suite following the “divide and conquer” principle.
<br>
ii) Instrumentation and monitoring customizing the SuT (Subject under Test)
infrastructure so that it reproduces real-world operational behavior and allowing
to gather relevant information during testing. 
<br>
iii) Test recommendation Using machine
learning and cognitive computing for recommending testing actions and providing
testers with friendly interactive facilities for decision taking.

# Documentation

The [ElasTest] project provides detailed documentation including tutorials,
installation and development guide.

# Source
Source code for other ElasTest projects can be found in the [GitHub ElasTest
Group].

# Support
If you need help and support with the EIM, please refer to the ElasTest [Bugtracker]. 
Here you can find the help you need.

# News
Follow us on Twitter @[ElasTest Twitter].

# Contribution policy
You can contribute to the ElasTest community through bug-reports, bug-fixes,
new code or new documentation. For contributing to the ElasTest community,
you can use the issue support of GitHub providing full information about your
contribution and its value. In your contributions, you must comply with the
following guidelines

 You must specify the specific contents of your contribution either through a
  detailed bug description, through a pull-request or through a patch.
 You must specify the licensing restrictions of the code you contribute.
 For newly created code to be incorporated in the ElasTest code-base, you
  must accept ElasTest to own the code copyright, so that its open source
  nature is guaranteed.
 You must justify appropriately the need and value of your contribution. The
  ElasTest project has no obligations in relation to accepting contributions
  from third parties.
 The ElasTest project leaders have the right of asking for further
  explanations, tests or validations of any code contributed to the community
  before it being incorporated into the ElasTest code-base. You must be ready
  to addressing all these kind of concerns before having your code approved.

# Licensing and distribution
Licensed under the Apache License, Version 2.0 (the License);
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

Contribution policy
-------------------

You can contribute to the ElasTest community through bug-reports, bug-fixes,
new code or new documentation. For contributing to the ElasTest community,
you can use GitHub, providing full information about your contribution and its
value. In your contributions, you must comply with the following guidelines

* You must specify the specific contents of your contribution either through a
  detailed bug description, through a pull-request or through a patch.
* You must specify the licensing restrictions of the code you contribute.
* For newly created code to be incorporated in the ElasTest code-base, you
  must accept ElasTest to own the code copyright, so that its open source
  nature is guaranteed.
* You must justify appropriately the need and value of your contribution. The
  ElasTest project has no obligations in relation to accepting contributions
  from third parties.
* The ElasTest project leaders have the right of asking for further
  explanations, tests or validations of any code contributed to the community
  before it being incorporated into the ElasTest code-base. You must be ready
  to addressing all these kind of concerns before having your code approved.

Support
-------

The ElasTest project provides community support through the [ElasTest Public
Mailing List] and through [StackOverflow] using the tag *elastest*.


<p align="center">
  <img src="http://elastest.io/images/logos_elastest/ue_logo-small.png"><br>
  Funded by the European Union
</p>

[Apache 2.0 License]: http://www.apache.org/licenses/LICENSE-2.0
[Atos Spain]: http://es.atos.net/
[ElasTest]: http://elastest.io/
[ElasTest Logo]: http://elastest.io/images/logos_elastest/elastest-logo-gray-small.png
[ElasTest Twitter]: https://twitter.com/elastestio
[GitHub ElasTest Group]: https://github.com/elastest
[Bugtracker]: https://github.com/elastest/bugtracker
