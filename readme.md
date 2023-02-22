# SplitWiser

## Contributors

* Barbara Gaweł-Kucab
* Jędrzej Ziebura
* Mikołaj Wielgos

## Changelog

[Available here](./docs/changelog.md)

## Architecture

![Overview](./docs/resources/overview.jfif)

### Legacy Model

 This model is the first draft that was created in the beginning of the project.

![Model](./docs/resources/model/model.svg)

New model is displayed below
*here should be the ss, draft in draw.io is ready*

### Views

During the implementation of the GUI/Client side the views changed slightly, but the core idea and 85% of the looks remain the same

![Login view](./docs/resources/views/login-view.svg)
![Create user view](./docs/resources/views/create-user-view.svg)

![Create group view](./docs/resources/views/create-group-view.svg)
![Summary view](./docs/resources/views/summary-view.svg)

![Create transaction view](./docs/resources/views/create-transaction-view.svg)

### Server endpoints

Base url: `/api`

#### user

* `GET /users`
* `GET /user/{id}`
* `POST /user/{id}/payments`

#### payment

* `GET /payments`
* `GET /payment/{id}`
* `POST /payment/group/{groupId}`

#### group

* `GET /groups`
* `GET /group/{id}`
* `GET /group/{id}/members`
* `GET /group/{id}/payments`
* `POST /group`

### Database

#### Datebase diagram

![Diagram](./docs/resources/database/database-schema.png)

#### Groups

![Groups](./docs/resources/database/groups.png)

#### Users

![Users](./docs/resources/database/users.png)

#### Payments

![Payments](./docs/resources/database/payments.png)

#### Payments receivers users

![PaymentsReceivers](./docs/resources/database/payments-receivers-users.png)

### JavaFx Views

![Login](./docs/resources/views/javafx/login.jpg)

![Create group](./docs/resources/views/javafx/create-group.jpg)

![Create user](./docs/resources/views/javafx/create-user.jpg)

![image](https://user-images.githubusercontent.com/92650724/218481003-2d6cf69c-4090-406c-8ca2-ce47602c6cd6.png)

![Create payment](./docs/resources/views/javafx/create-payment.jpg)

![image](https://user-images.githubusercontent.com/92650724/218481256-3f60d466-df37-405f-b879-32dd7925fae0.png)

